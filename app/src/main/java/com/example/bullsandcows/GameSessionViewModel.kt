package com.example.bullsandcows

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

data class Guess(val guess: String, val bulls: Int, val cows: Int)

class GameSessionViewModel : ViewModel() {
    private val guessList = mutableListOf<Guess>()
    private val lastGuess = MutableLiveData<Guess>()
    private lateinit var target: String

    fun observeLastGuess(lifecycleOwner: LifecycleOwner, observer: Observer<Guess>) {
        lastGuess.observe(lifecycleOwner, observer)
    }

    fun getGuessCount(): Int {
        return guessList.size
    }

    fun submitGuess(guess: String): Boolean {
        if (target == guess) {
            return true
        }
        val bc = BullsAndCowsHelper.getBullsAndCows(guess, target)
        val guessVal = Guess(guess, bc[0], bc[1])
        guessList.add(guessVal)
        lastGuess.value = guessVal
        return false
    }

    fun generateTarget() {
        val digits = mutableListOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
        digits.shuffle()
        target = digits.subList(0, 4).joinToString("")
    }

    fun getTargetNumber(): String {
        return target
    }
}

class GameSessionViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameSessionViewModel::class.java)) {
            return GameSessionViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}