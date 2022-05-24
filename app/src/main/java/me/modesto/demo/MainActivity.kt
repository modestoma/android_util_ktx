package me.modesto.demo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import me.modesto.utils.Utils
import me.modesto.utils.hint.logInfo
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalTime::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainPage()
        }
        Utils.init(this.applicationContext)
//            .initLogger(enable = true, defaultTag = "android_util_ktx")
            .initLoggerSave(true)
        logInfo(null)
        val time = measureTime {
            repeat(1000) {
                logInfo("test $it")
            }
        }
        logInfo("time cost: $time")
        val testTime = measureTime {
            Log.d("MLQ", "test")
        }
        logInfo("test time cost: $testTime")
    }
}
