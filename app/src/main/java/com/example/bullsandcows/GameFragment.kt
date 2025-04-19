package com.example.bullsandcows

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bullsandcows.databinding.FragmentGameBinding


class GameFragment : Fragment() {

    lateinit var binding: FragmentGameBinding
    lateinit var viewModel: GameSessionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this, GameSessionViewModelFactory()).get(GameSessionViewModel::class.java)
        viewModel.generateTarget()

        binding.editTextText.setOnKeyListener({
                v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                binding.submitButton.performClick()
            }
            false
        })

        setSubmitButton()
        setEndGameButton()
        setRecyclerView()

        return binding.root
    }

    fun setSubmitButton() {
        binding.submitButton.setOnClickListener{
            val guess = binding.editTextText.text.toString()
            if (!BullsAndCowsUtil.isValidGuess(guess)) {
                Toast.makeText(context, "Invalid guess", Toast.LENGTH_SHORT).show()
            } else {
                if (viewModel.submitGuess(guess)) {
                    val res = Bundle()
                    res.putBoolean("pass", true)
                    res.putInt("guess_count", viewModel.getGuessCount())
                    res.putString("target", viewModel.getTargetNumber())
                    it.findNavController().navigate(R.id.action_gameFragment_to_resultFragment, res)
                }
            }
            binding.editTextText.setText("")
        }
    }

    fun setEndGameButton() {
        binding.endGameButton.setOnClickListener {
            val res = Bundle()
            res.putBoolean("pass", false)
            res.putInt("guess_count", viewModel.getGuessCount())
            res.putString("target", viewModel.getTargetNumber())
            it.findNavController().navigate(R.id.action_gameFragment_to_resultFragment, res)
        }
    }

    fun setRecyclerView() {
        binding.inputRecView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.inputRecView.adapter = GameGuessListAdapter()
        viewModel.lastGuess.observe(viewLifecycleOwner) {
            val adapter = binding.inputRecView.adapter as GameGuessListAdapter
            adapter.addGuess(it)
            binding.inputRecView.scrollToPosition(adapter.itemCount - 1)
        }
    }

}