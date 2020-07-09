package axel.legue.multithreadingsample

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import axel.legue.multithreadingsample.databinding.ActivityMainBinding

const val MESSAGE_KEY = "message_key"

const val fileUrl = "https://2833069.youcanlearnit.net/lorem_ipsum.txt"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

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

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.myData.observe(this, Observer {
            log(it)
        })
    }

    /**
     * Run some code
     */
    private fun runCode() {
        viewModel.doWork()
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


}