package com.example.wordle

import FourLetterWordList.getRandomFourLetterWord
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

val answer = getRandomFourLetterWord()

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setListeners(findViewById(R.id.input_1), findViewById(R.id.result_1))
        setListeners(findViewById(R.id.input_2), findViewById(R.id.result_2))
        setListeners(findViewById(R.id.input_3), findViewById(R.id.result_3))
    }

    private fun setListeners(guessView: EditText, answerView: TextView) {
        guessView.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val text = v.text.toString().toUpperCase()
                if (text.length == 4) {
                    guessView.isEnabled = false
                    val result = checkGuess(answer, text)
                    answerView.setText(result)
                    if (result == "OOOO") {
                        Toast.makeText(this@MainActivity, "You guessed correctly!", Toast.LENGTH_SHORT).show()
                    }
                    else if (guessView.id.equals(R.id.input_1)) {
                        findViewById<EditText>(R.id.input_2).isEnabled = true
                    }
                    else if (guessView.id.equals(R.id.input_2)) {
                        findViewById<EditText>(R.id.input_3).isEnabled = true
                    }
                    else {
                        Toast.makeText(this@MainActivity, "All guesses completed!", Toast.LENGTH_SHORT).show()
                        Toast.makeText(this@MainActivity, "The answer was $answer", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "4-character guesses only!", Toast.LENGTH_SHORT).show()
                }
                return@OnEditorActionListener true
            }
            false
        })

    }

    /**
     * Parameters / Fields:
     *   wordToGuess : String - the target word the user is trying to guess
     *   guess : String - what the user entered as their guess
     *
     * Returns a String of 'O', '+', and 'X', where:
     *   'O' represents the right letter in the right place
     *   '+' represents the right letter in the wrong place
     *   'X' represents a letter not in the target word
     */
    private fun checkGuess(wordToGuess: String, guess: String) : String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            }
            else if (guess[i] in wordToGuess) {
                result += "+"
            }
            else {
                result += "X"
            }
        }
        return result
    }
}