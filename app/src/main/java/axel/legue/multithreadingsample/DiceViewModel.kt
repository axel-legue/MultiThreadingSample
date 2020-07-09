package axel.legue.multithreadingsample

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class DiceViewModel : ViewModel() {

    private var diceValue = MutableLiveData<Pair<Int, Int>>()

    fun rollDices() {

        for (i in 0..4) {
            viewModelScope.launch {
                for (j in 1..20) {
                    diceValue.value = Pair(i, Random.nextInt(1, 6))
                    delay(100)
                }
            }
        }
    }

    fun getPair(): MutableLiveData<Pair<Int, Int>> {
        return diceValue
    }

}