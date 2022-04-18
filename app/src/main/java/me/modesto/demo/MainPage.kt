package me.modesto.demo

import android.content.Context
import android.hardware.fingerprint.FingerprintManager
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat.getSystemService
import me.modesto.demo.page.BarUtilsPage
import me.modesto.utils.AppContext
import me.modesto.utils.Logger


/**
 * Description.
 *
 * @author Created by Modesto in 2022/4/20
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage() {
    MaterialTheme {
        Scaffold {
            BarUtilsPage()
            test()
        }
    }
}

private fun test() {
    val fm = AppContext.current.getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager
    try {
        val clazz = Class.forName("android.hardware.fingerprint.FingerprintManager")
        val printsMethod = clazz.getDeclaredMethod("getEnrolledFingerprints")
        printsMethod.isAccessible = true
        val list = printsMethod.invoke(fm)
//        Logger.dTag("MLQ", "count: ${list.size}")
    } catch (e: NoSuchMethodException) {
        e.printStackTrace()
    }
}