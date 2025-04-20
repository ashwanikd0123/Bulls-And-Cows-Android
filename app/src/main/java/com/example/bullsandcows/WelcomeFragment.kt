package com.example.bullsandcows

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.bullsandcows.databinding.FragmentWelcomeBinding


class WelcomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        binding.startGameButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_welcomeFragment_to_gameFragment)
        }
        return binding.root
    }
}