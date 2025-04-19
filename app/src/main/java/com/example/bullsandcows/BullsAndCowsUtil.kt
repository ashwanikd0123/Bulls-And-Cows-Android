package com.example.bullsandcows

class BullsAndCowsUtil {
    companion object {
        fun getBullsAndCows(guess: String, target: String): List<Int> {
            val secretCount = mutableMapOf<Char, Int>()
            val guessCount = mutableMapOf<Char, Int>()
            var bulls = 0
            var cows = 0
            for (i in guess.indices) {
                if (target[i] == guess[i]) {
                    bulls++
                } else {
                    if (secretCount.containsKey(guess[i]) && secretCount[guess[i]] != 0) {
                        secretCount[guess[i]] = secretCount[guess[i]]!! - 1
                        cows++
                    } else {
                        guessCount[guess[i]] = guessCount.getOrDefault(guess[i], 0) + 1
                    }

                    if (guessCount.containsKey(target[i]) && guessCount[target[i]] != 0) {
                        guessCount[target[i]] = guessCount[target[i]]!! - 1
                        cows++
                    } else {
                        secretCount[target[i]] = secretCount.getOrDefault(target[i], 0) + 1
                    }
                }
            }
            return listOf(bulls, cows)
        }

        fun isValidGuess(guess: String): Boolean {
            if (guess.length != 4) {
                return false
            }
            for (char in guess) {
                if (!char.isDigit()) {
                    return false
                }
            }
            return true
        }
    }
}