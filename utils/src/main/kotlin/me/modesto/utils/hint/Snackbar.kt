@file:Suppress("NOTHING_TO_INLINE")

package me.modesto.utils.hint

import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

/**
 * Description.
 *
 * @author Created by Modesto in 2022/4/21
 */

///////////////////////////////////////////////////////////////////////////
// SnackBar Show
///////////////////////////////////////////////////////////////////////////

inline fun View.snack(msg: CharSequence,
                      duration: Int = Snackbar.LENGTH_SHORT,
                      actionSetup: Snackbar.() -> Unit = {}) =
    Snackbar.make(this, msg, duration).apply(actionSetup).also { it.show() }

inline fun View.snack(@StringRes strResId: Int,
                      duration: Int = Snackbar.LENGTH_SHORT,
                      actionSetup: Snackbar.() -> Unit = {}) =
    Snackbar.make(this, strResId, duration).apply(actionSetup).also { it.show() }

inline fun View.longSnack(msg: CharSequence,
                          actionSetup: Snackbar.() -> Unit = {}) =
    snack(msg, Snackbar.LENGTH_LONG, actionSetup)

inline fun View.longSnack(@StringRes strResId: Int,
                          actionSetup: Snackbar.() -> Unit = {}) =
    snack(strResId, Snackbar.LENGTH_LONG, actionSetup)

inline fun View.foreverSnack(msg: CharSequence,
                             actionSetup: Snackbar.() -> Unit = {}) =
    snack(msg, Snackbar.LENGTH_INDEFINITE, actionSetup)

inline fun View.foreverSnack(@StringRes strResId: Int,
                             actionSetup: Snackbar.() -> Unit = {}) =
    snack(strResId, Snackbar.LENGTH_INDEFINITE, actionSetup)

///////////////////////////////////////////////////////////////////////////
// SnackBar Action
///////////////////////////////////////////////////////////////////////////

inline fun Snackbar.action(text: CharSequence,
                           @ColorInt textColor: Int? = null,
                           crossinline onClick: () -> Unit) {
    setAction(text) { onClick() }
    if (null != textColor) setActionTextColor(textColor)
}

inline fun Snackbar.action(@StringRes textResId: Int,
                           @ColorInt textColor: Int? = null,
                           crossinline onClick: () -> Unit) {
    setAction(textResId) { onClick() }
    if (null != textColor) setActionTextColor(textColor)
}

///////////////////////////////////////////////////////////////////////////
// SnackBar Event
///////////////////////////////////////////////////////////////////////////

inline fun Snackbar.onDismiss(crossinline onDismiss: (event: Int) -> Unit) {
    addCallback(object : Snackbar.Callback() {
        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
            onDismiss.invoke(event)
        }
    })
}