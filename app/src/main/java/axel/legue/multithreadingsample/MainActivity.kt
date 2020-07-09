package axel.legue.multithreadingsample

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import axel.legue.multithreadingsample.databinding.DiceLayoutBinding
import kotlin.concurrent.thread
import kotlin.random.Random

const val MESSAGE_KEY = "message_key"
const val DICE_INDEX_KEY = "die_index_key"
const val DICE_VALUE_KEY = "die_value_key"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: DiceLayoutBinding
    private lateinit var drawables: Array<Int>
    private lateinit var imageViews: Array<ImageView>

    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            val bundle = msg.data
            val diceIndex = bundle.getInt(DICE_INDEX_KEY, 0)
            val diceValue = bundle.getInt(DICE_VALUE_KEY, 1)
            imageViews[diceIndex].setImageResource(drawables[diceValue - 1])
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize view binding for view object references
        binding = DiceLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageViews = arrayOf(
            binding.die1,
            binding.die2,
            binding.die3,
            binding.die4,
            binding.die5
        )

        drawables = arrayOf(
            R.drawable.dice_1,
            R.drawable.dice_2,
            R.drawable.dice_3,
            R.drawable.dice_4,
            R.drawable.dice_5,
            R.drawable.dice_6
        )
        // Initialize button click handlers
        with(binding) {
            rollButton.setOnClickListener { rollDices() }
        }
    }

    private fun rollDices() {

        for (diceIndex in imageViews.indices) {
            thread(start = true) {
                for (i in 1..20) {
                    val mess = Message()
                    mess.data.putInt(DICE_INDEX_KEY, diceIndex)
                    mess.data.putInt(DICE_VALUE_KEY, getDiceValue())
                    handler.sendMessage(mess)
                    Thread.sleep(100)
                }
            }
        }
    }

    private fun getDiceValue(): Int {
        return Random.nextInt(1, 7)
    }

}