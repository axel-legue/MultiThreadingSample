package axel.legue.multithreadingsample

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import java.net.URL
import java.nio.charset.Charset

class MainViewModel : ViewModel() {
    val myData = MutableLiveData<String>()
    private lateinit var job: Job

    fun doWork() {
        //MainThread Scope
        job = viewModelScope.launch {
            myData.value = fetchSomething()
        }
    }

    fun cancelJob() {
        try {
            job.cancel()
            myData.value = "Job Cancelled"
        } catch (ignore: UninitializedPropertyAccessException) {
        }
    }

    // Suspend mean that the function can be paused
    private suspend fun fetchSomething(): String? {
        delay(3000)
        //Switch to a background Thread  ==> "withContext"
        return withContext(Dispatchers.IO) {
            val url = URL(fileUrl)
            return@withContext url.readText(Charset.defaultCharset())
        }
    }


}