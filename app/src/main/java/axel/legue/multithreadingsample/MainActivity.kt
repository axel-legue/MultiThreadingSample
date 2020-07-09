package axel.legue.multithreadingsample

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import axel.legue.multithreadingsample.databinding.DiceLayoutBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: DiceLayoutBinding
    private lateinit var viewModel: DiceViewModel
    private lateinit var drawables: Array<Int>
    private lateinit var imageViews: Array<ImageView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize view binding for view object references
        binding = DiceLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageViews = arrayOf(
            binding.die1, binding.die2,
            binding.die3, binding.die4,
            binding.die5
        )
        drawables = arrayOf(
            R.drawable.dice_1, R.drawable.dice_2,
            R.drawable.dice_3, R.drawable.dice_4,
            R.drawable.dice_5, R.drawable.dice_6
        )
        viewModel = ViewModelProvider(this).get(DiceViewModel::class.java)

        with(binding) {
            rollButton.setOnClickListener { viewModel.rollDices() }
        }

        viewModel.getPair().observe(this, Observer {
            imageViews[it.first].setImageResource(drawables[it.second - 1])
        })
    }

}