package com.example.bullsandcows

import android.os.Bundle
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
    private lateinit var binding: FragmentGameBinding
    private lateinit var viewModel: GameSessionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this, GameSessionViewModelFactory())[GameSessionViewModel::class.java]
        viewModel.generateTarget()

        binding.submitButton.setOnClickListener{
            submitText(it)
            clearInput()
        }

        binding.endGameButton.setOnClickListener {
            endGame(it)
        }

        setRecyclerView()

        return binding.root
    }

    private fun clearInput() {
        binding.editTextText.setText("")
    }

    private fun submitText(v: View) {
        val guess = binding.editTextText.text.toString()
        if (!BullsAndCowsHelper.isValidGuess(guess)) {
            Toast.makeText(context, "Invalid guess", Toast.LENGTH_SHORT).show()
        } else {
            if (viewModel.submitGuess(guess)) {
                val res = Bundle()
                res.putBoolean("pass", true)
                res.putInt("guess_count", viewModel.getGuessCount())
                res.putString("target", viewModel.getTargetNumber())
                v.findNavController().navigate(R.id.action_gameFragment_to_resultFragment, res)
            }
        }
    }

    private fun endGame(v: View) {
        val res = Bundle()
        res.putBoolean("pass", false)
        res.putInt("guess_count", viewModel.getGuessCount())
        res.putString("target", viewModel.getTargetNumber())
        v.findNavController().navigate(R.id.action_gameFragment_to_resultFragment, res)
    }

    private fun setRecyclerView() {
        binding.inputRecView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.inputRecView.adapter = GameGuessListAdapter()
        viewModel.observeLastGuess(viewLifecycleOwner) {
            val adapter = binding.inputRecView.adapter as GameGuessListAdapter
            adapter.addGuess(it)
            binding.inputRecView.scrollToPosition(adapter.itemCount - 1)
        }
    }
}