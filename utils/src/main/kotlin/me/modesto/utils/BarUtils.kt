package me.modesto.utils

import android.content.Context
import android.util.TypedValue
import android.view.Window

/**
 * Description.
 *
 * @author Created by Modesto in 2022/4/20
 */
///////////////////////////////////////////////////////////////////////////
// height
///////////////////////////////////////////////////////////////////////////
fun Context.statusBarHeight(): Int {
    val resId = resources.getIdentifier("status_bar_height", "dimen", "android")
    return if (0 != resId) resources.getDimensionPixelSize(resId) else 0
}

fun Context.actionBarHeight(): Int {
    val typeValue = TypedValue()
    if (this.theme.resolveAttribute(android.R.attr.actionBarSize, typeValue, true)) {
        return TypedValue.complexToDimensionPixelSize(typeValue.data, this.resources.displayMetrics)
    }
    return 0
}

fun Context.navigationBarHeight(): Int {
    val resId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
    return if (0 != resId) resources.getDimensionPixelSize(resId) else 0
}

///////////////////////////////////////////////////////////////////////////
// status bar
///////////////////////////////////////////////////////////////////////////
fun Window.setSystemUiVisible(isVisible: Boolean) {
}