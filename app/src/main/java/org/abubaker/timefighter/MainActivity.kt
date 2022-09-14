package org.abubaker.timefighter

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import org.abubaker.timefighter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var score = 0

    private var gameStarted = false

    private lateinit var countDownTimer: CountDownTimer
    private val initialCountDown: Long = 10000 // 10 Seconds
    private val countDownInterval: Long = 1000

    // onCreate()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // Inflate Layout: @layout/activity_main.xml
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.tapMeButton.setOnClickListener { _ ->
            incrementScore()
        }

        // It will display the initial (default) value of the score = 0
        // binding.gameScoreTextView.text = getString(R.string.yourScore, score)
        resetGame()
    }

    private fun resetGame() {
        score = 0

        binding.gameScoreTextView.text = getString(R.string.yourScore, score)

        val initialTimeLeft = initialCountDown / 1000
        binding.timeLeftTextView.text = getString(R.string.timeLeft, initialTimeLeft)

        countDownTimer = object : CountDownTimer(initialCountDown, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                val timeLeft = millisUntilFinished / 1000
                binding.timeLeftTextView.text = getString(R.string.timeLeft, timeLeft)
            }

            override fun onFinish() {
                endGame()
            }

        }

        gameStarted = false
    }

    private fun incrementScore() {
        if (!gameStarted) {
            startGame()
        }

        score += 1
        val newScore = getString(R.string.yourScore, score)
        binding.gameScoreTextView.text = newScore
    }

    private fun startGame() {
        countDownTimer.start()
        gameStarted = true
    }

    private fun endGame() {
        Toast.makeText(this, getString(R.string.gameOverMessage, score), Toast.LENGTH_SHORT).show()
        resetGame()
    }

}