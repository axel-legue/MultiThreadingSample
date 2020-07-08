package axel.legue.multithreadingsample

import android.os.*
import android.util.Log
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import axel.legue.multithreadingsample.databinding.ActivityMainBinding

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
        //  Runnable Execute the operation on a background thread and at the end of the stack adn execute it immediately when all the operation are done or with a delay if requested
        //  Handler handle the communication between the background Thread and the UI Thread

        Handler().postAtTime({ log("Posting at a certain time") }, SystemClock.uptimeMillis() + 4000)
        Handler().postDelayed({ log("operation from runnable 1s") }, 1000)
        Handler().postDelayed({ log("operation from runnable 2s") }, 2000)
        Handler().postDelayed({ log("operation from runnable 3s") }, 3000)

        log("synchronous operation A")
        log("synchronous operation B")
        log("synchronous operation C")

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