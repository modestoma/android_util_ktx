package me.modesto.utils.system

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.view.WindowManager
import me.modesto.utils.AppContext

/**
 * get screen width
 *
 * @return screen width
 * @author Created by Modesto in 2022/4/18
 */
fun getScreenWidth(): Int {
    val windowManager = AppContext.current.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    return if (Build.VERSION_CODES.R <= Build.VERSION.SDK_INT) {
        val windowMetrics = windowManager.currentWindowMetrics
        val rect = windowMetrics.bounds
        rect.right - rect.left
    } else {
        val point = Point()
        @Suppress("DEPRECATION")
        windowManager.defaultDisplay.getSize(point)
        point.x
    }
}

/**
 * get screen height
 *
 * @return screen height
 * @author Created by Modesto in 2022/4/18
 */
fun getScreenHeight(): Int {
    val windowManager = AppContext.current.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    return if (Build.VERSION_CODES.R <= Build.VERSION.SDK_INT) {
        val windowMetrics = windowManager.currentWindowMetrics
        val rect = windowMetrics.bounds
        rect.bottom - rect.top
    } else {
        val point = Point()
        @Suppress("DEPRECATION")
        windowManager.defaultDisplay.getSize(point)
        point.y
    }
}