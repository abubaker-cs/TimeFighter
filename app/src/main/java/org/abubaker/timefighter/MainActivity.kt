package org.abubaker.timefighter

import android.os.Bundle
import android.os.CountDownTimer
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
    private var gameStarted = false

    private lateinit var countDownTimer: CountDownTimer
    private var initialCountDown: Long = 60000
    private var countDownInterval: Long = 1000
    private var timeLeft = 60


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

        // val newScore = "Your Score: $score"
        val newScore = getString(R.string.your_score, score)
        gameScoreTextView.text = newScore

    }

    // Reset game logic
    private fun resetGame() {
        score = 0

        val initialScore = getString(R.string.your_score, score)
        gameScoreTextView.text = initialScore

        val initialTimeLeft = getString(R.string.time_left, 60)
        timeLeftTextView.text = initialTimeLeft

        countDownTimer = object : CountDownTimer(initialCountDown, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished.toInt() / 1000

                val timeLeftString = getString(R.string.time_left, timeLeft)
                timeLeftTextView.text = timeLeftString
            }

            override fun onFinish() {
                // TODO("Not yet implemented")
            }
        }

        gameStarted = false
    }

    // start game logic
    private fun startGame() {

    }

    // end game logic
    private fun endGame() {

    }

}