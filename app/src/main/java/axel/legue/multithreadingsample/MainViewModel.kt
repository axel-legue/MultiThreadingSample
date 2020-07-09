package axel.legue.multithreadingsample

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import java.nio.charset.Charset

class MainViewModel: ViewModel() {
    val myData = MutableLiveData<String>()

    fun doWork(){
        //MainThread Scope
        viewModelScope.launch{
            myData.value = fetchSomething()
        }
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