package com.example.bullsandcows

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.bullsandcows.databinding.SimpleViewItemBinding

class GameGuessListAdapter : Adapter<GameGuessListAdapter.GameListViewHolder>() {
    val guessList = mutableListOf<Guess>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameListViewHolder {
        val binding = SimpleViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameListViewHolder, position: Int) {
        holder.guessTextView.text = "Guess: " + guessList[position].guess
        holder.bullsTextView.text = "Bulls: " + guessList[position].bulls
        holder.cowsTextView.text = "Cows: " + guessList[position].cows
    }

    fun addGuess(guess: Guess) {
        guessList.add(guess)
        notifyItemInserted(guessList.size - 1)
    }

    override fun getItemCount(): Int {
        return guessList.size
    }

    class GameListViewHolder(binding: SimpleViewItemBinding) : ViewHolder(binding.root) {
        val guessTextView = binding.guessText
        val bullsTextView = binding.bullsText
        val cowsTextView = binding.cowsText
    }
}