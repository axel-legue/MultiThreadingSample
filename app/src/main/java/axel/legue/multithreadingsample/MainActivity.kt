package axel.legue.multithreadingsample

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import axel.legue.multithreadingsample.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize view binding for view object references
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize button click handlers
        with(binding) {
            runButton.setOnClickListener { runCode() }
            clearButton.setOnClickListener { clearOutput() }
        }

    }

    /**
     * Run some code
     */
    private fun runCode() {
        // Object declaration
        Thread{
            for (i in 1..10) {
                Log.i(LOG_TAG, "Looping $i")
                Thread.sleep(1000)
            }
            Log.i(LOG_TAG, "All done!")
        }.start()

        // Extension function in Kotlin
        thread(start = true) {
            for (i in 1..10) {
                Log.i(LOG_TAG, "Looping $i")
                Thread.sleep(1000)
            }
            Log.i(LOG_TAG, "All done!")
        }



    }

    /**
     * Clear log display
     */
    private fun clearOutput() {
        binding.logDisplay.text = ""
        scrollTextToEnd()
    }

    /**
     * Log output to logcat and the screen
     */
    @Suppress("SameParameterValue")
    private fun log(message: String) {
        Log.i(LOG_TAG, message)
        binding.logDisplay.append(message + "\n")
        scrollTextToEnd()
    }

    /**
     * Scroll to end. Wrapped in post() function so it's the last thing to happen
     */
    private fun scrollTextToEnd() {
        Handler().post { binding.scrollView.fullScroll(ScrollView.FOCUS_DOWN) }
    }

    class Toto {
        val name = "toto"

    }
}