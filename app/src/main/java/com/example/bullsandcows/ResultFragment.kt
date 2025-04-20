package com.example.bullsandcows

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.bullsandcows.databinding.FragmentResultBinding

class ResultFragment : Fragment() {
    private lateinit var binding: FragmentResultBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultBinding.inflate(inflater, container, false)
        val passed = arguments?.getBoolean("pass", false)?: false
        val guessCount = arguments?.getInt("guess_count", -1)?: -1
        val target = arguments?.getString("target", "")?: ""

        if (passed) {
            binding.resultTextView.setTextColor(Color.GREEN)
            binding.resultTextView.text = "You won"
        } else {
            binding.resultTextView.setTextColor(Color.RED)
            binding.resultTextView.text = "You lost"
        }

        binding.targetText.text = "Secret: $target"
        binding.guessCountTextView.text = "Guess Count: $guessCount"

        binding.homeButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_resultFragment_to_welcomeFragment)
        }

        binding.playAgainButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_resultFragment_to_gameFragment)
        }

        return binding.root
    }

}