package me.modesto.demo

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import me.modesto.utils.hint.Logger
import me.modesto.utils.Utils
import me.modesto.utils.permissions.hasPermissions

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.init(this.applicationContext) {
            enable = true
        }
        setContent {
            MainPage()
        }
        val flag = hasPermissions(Manifest.permission.ACCESS_NETWORK_STATE)
        Logger.dTag("MLQ", "has permission: $flag")
    }
}
