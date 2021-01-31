package org.abubaker.timefighter

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // DEBUGGING - Setup
    private val TAG = MainActivity::class.java.simpleName

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

        // DEBUGGING - Trace changes here
        Log.d(TAG, "onCreate called. Score is: $score")


        gameScoreTextView = findViewById(R.id.game_score_text_view)
        timeLeftTextView = findViewById(R.id.time_left_text_view)
        tapButton = findViewById(R.id.tap_me_button)

        tapButton.setOnClickListener { v ->

            // Applying Animation
            val bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce)
            v.startAnimation(bounceAnimation)

            incrementScore()
        }

        if (savedInstanceState != null) {
            score = savedInstanceState.getInt(SCORE_KEY)
            timeLeft = savedInstanceState.getInt(TIME_LEFT_KEY)

            restoreGame()

        } else {
            resetGame()
        }
    }


    // Increment score logic
    private fun incrementScore() {

        score++

        // val newScore = "Your Score: $score"
        val newScore = getString(R.string.your_score, score)
        gameScoreTextView.text = newScore

        if (!gameStarted) {
            startGame()
        }

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
                endGame()
            }
        }

        gameStarted = false
    }

    // Restore values after configuration change
    private fun restoreGame() {

        // Your Score
        val restoredScore = getString(R.string.your_score, score)
        gameScoreTextView.text = restoredScore

        // Time Left
        val restoredTime = getString(R.string.time_left, timeLeft)
        timeLeftTextView.text = restoredTime

        countDownTimer = object : CountDownTimer((timeLeft * 1000).toLong(), countDownInterval) {

            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished.toInt() / 1000

                val timeLeftString = getString(R.string.time_left, timeLeft)
                timeLeftTextView.text = timeLeftString
            }

            override fun onFinish() {
                endGame()
            }
        }

        countDownTimer.start()
        gameStarted = true

    }

    // start game logic
    private fun startGame() {
        countDownTimer.start()
        gameStarted = true
    }

    // end game logic
    private fun endGame() {
        Toast.makeText(this, getString(R.string.game_over_message, score), Toast.LENGTH_LONG).show()
        resetGame()
    }

    /*****
     * Preparing the app for saving data from being lost due to "Configuration Change"
     */
    companion object {
        private const val SCORE_KEY = "SCORE_KEY"
        private const val TIME_LEFT_KEY = "TIME_LEFT_KEY"
    }

    // Prepare important DATA before the app will be destroyed due to configuration change
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(SCORE_KEY, score)
        outState.putInt(TIME_LEFT_KEY, timeLeft)

        countDownTimer.cancel()

        Log.d(TAG, "onSaveInstanceState: Saving Score: $score & Time Left: $timeLeft")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(TAG, "onDestroy Called")
    }

    /***
     * Options menu - for About Developer
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        // Inflate the menu | This adds item to the action bar if it is present
        super.onCreateOptionsMenu(menu)

        // Inflate menu from @res/menu/menu.xml
        menuInflater.inflate(R.menu.menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.about_item) {
            showInfo()
        }

        return true
    }

    // Markup for the Dialog
    private fun showInfo() {

        // Define parameters
        val dialogTitle = getString(R.string.about_title, BuildConfig.VERSION_NAME)
        val dialogMessage = getString(R.string.about_message)

        // Attach parameters
        val builder = AlertDialog.Builder(this)
        builder.setTitle(dialogTitle)
        builder.setMessage(dialogMessage)

        // Show the dialog
        builder.create().show()
        
    }
}