package me.modesto.demo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import me.modesto.demo.ui.theme.AndroidUtilKtxTheme
import me.modesto.utils.AppContext
import me.modesto.utils.Logger
import me.modesto.utils.Utils
import me.modesto.utils.execCommand

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val commandResult = execCommand("getprop", isNeedResult = true)
        Log.d("MLQ", "Greeting: $commandResult")
        Utils.init(context = this)
        Log.d("MLQ", "onCreate: ${Logger.init()}")
        setContent {
            AndroidUtilKtxTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AndroidUtilKtxTheme {
        Greeting("Android")
    }
}