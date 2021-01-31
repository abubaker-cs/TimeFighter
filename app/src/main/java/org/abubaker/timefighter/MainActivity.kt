package org.abubaker.timefighter

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // We will connect views to these variables later on
    private lateinit var gameScoreTextView: TextView
    private lateinit var timeLeftTextView: TextView
    private lateinit var tapButton: Button

    // DATA
    private var score = 0


    // onCreate - Connect views to the variables
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameScoreTextView = findViewById(R.id.game_score_text_view)
        timeLeftTextView = findViewById(R.id.time_left_text_view)
        tapButton = findViewById(R.id.tap_me_button)

        tapButton.setOnClickListener {
            incrementScore()
        }
    }


    // Increment score logic
    private fun incrementScore() {

        score++

        val newScore = "Your Score: $score"
        gameScoreTextView.text = newScore

    }

    // Reset game logic
    private fun resetGame() {

    }

    // start game logic
    private fun startGame() {

    }

    // end game logic
    private fun endGame() {

    }

}