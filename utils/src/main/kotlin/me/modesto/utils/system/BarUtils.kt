package me.modesto.utils.system

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment

/**
 * Description.
 *
 * @author Created by Modesto in 2022/4/20
 */
///////////////////////////////////////////////////////////////////////////
// height
///////////////////////////////////////////////////////////////////////////
val Context?.statusBarHeight: Int
    get() {
        this ?: return 0
        val resId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (0 != resId)
            resources.getDimensionPixelSize(resId)
        else
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24F, Resources.getSystem().displayMetrics).toInt()
    }

val Context?.navigationBarHeight: Int
    get() {
        this ?: return 0
        val resId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (0 != resId) resources.getDimensionPixelSize(resId) else 0
    }

fun Context.actionBarHeight(): Int {
    val typeValue = TypedValue()
    if (this.theme.resolveAttribute(android.R.attr.actionBarSize, typeValue, true)) {
        return TypedValue.complexToDimensionPixelSize(typeValue.data, this.resources.displayMetrics)
    }
    return 0
}

///////////////////////////////////////////////////////////////////////////
// status bar
///////////////////////////////////////////////////////////////////////////
val Activity?.hasNavigationBar: Boolean
    get() {
        this ?: return false
        val decorView = window.decorView as? ViewGroup ?: return false
        for (index in 0 until decorView.childCount) {
            decorView.getChildAt(index).context.packageName
            if (-1 != decorView.getChildAt(index).id &&
                "navigationBarBackground" == resources.getResourceEntryName(decorView.getChildAt(index).id)
            ) {
                return true
            }
        }
        return false
    }

///////////////////////////////////////////////////////////////////////////
// Color
///////////////////////////////////////////////////////////////////////////
fun Activity.statusBarColor(@ColorInt color: Int) {
    window.statusBarColor = color
}

fun Activity.statusBarColorRes(@ColorRes colorRes: Int) = statusBarColor(resources.getColor(colorRes, null))

fun Fragment.statusBarColor(@ColorInt color: Int) = activity?.statusBarColor(color)

fun Fragment.statusBarColorRes(@ColorRes colorRes: Int) = activity?.statusBarColorRes(colorRes)

