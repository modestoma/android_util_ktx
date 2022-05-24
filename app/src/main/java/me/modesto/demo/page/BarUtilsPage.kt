package me.modesto.demo.page

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import me.modesto.utils.hint.toast
import me.modesto.utils.system.actionBarHeight
import me.modesto.utils.system.navigationBarHeight
import me.modesto.utils.system.statusBarHeight

/**
 * Description.
 *
 * @author Created by Modesto in 2022/4/20
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarUtilsPage() {
    val context = LocalContext.current
    Scaffold(
        topBar = { SmallTopAppBar(title = { Text(text = "BarUtilsPage") }) }
    ) {
        Column {
            Button(onClick = { context.toast("StatusBar height: ${context.statusBarHeight}") }) {
                Text(text = "Get StatusBar height")
            }
            Button(onClick = { context.toast("ActionBar height: ${context.actionBarHeight()}") }) {
                Text(text = "Get ActionBar height")
            }
            Button(onClick = { context.toast("NavigationBar height: ${context.navigationBarHeight}") }) {
                Text(text = "Get NavigationBar height")
            }
        }
    }
}