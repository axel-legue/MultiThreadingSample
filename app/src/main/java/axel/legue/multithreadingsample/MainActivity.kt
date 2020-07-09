package axel.legue.multithreadingsample

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import axel.legue.multithreadingsample.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import java.nio.charset.Charset

const val MESSAGE_KEY = "message_key"

const val fileUrl = "https://2833069.youcanlearnit.net/lorem_ipsum.txt"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            val bundle = msg.data
            val message = bundle?.getString(MESSAGE_KEY) + " handler"
            log(message ?: "message was null")
        }
    }

    /**
     * Coroutines Context  - 3 available
     *      1 - Dispatchers.Main(UI Thread) : For very short tasks that don't need to run in the background
     *      2 - Dispatchers.IO(Background Thread) : For storage and network access
     *      3 - Dispatchers.Default(Background Thread) : For CPU-intensive work
     */

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
        CoroutineScope(Dispatchers.Main).launch {
            val result = fetchSomething()
            log(result ?: "Null")
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

    // Suspend mean that the function can be paused
    private suspend fun fetchSomething(): String? {
        //Switch to a background Thread  ==> "withContext"
        return withContext(Dispatchers.IO) {
            val url = URL(fileUrl)
            return@withContext url.readText(Charset.defaultCharset())
        }
    }

}