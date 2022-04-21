package me.modesto.demo

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import me.modesto.demo.page.BarUtilsPage
import me.modesto.utils.locationManager
import me.modesto.utils.mediaRouter


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
            mediaRouter
        }
    }
}