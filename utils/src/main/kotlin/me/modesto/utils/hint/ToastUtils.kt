@file:Suppress("NOTHING_TO_INLINE")

package me.modesto.utils.hint

import android.content.Context
import android.widget.Toast
import me.modesto.utils.AppContext

/**
 * Description.
 *
 * @author Created by Modesto in 2022/4/20
 */
inline fun Context.toast(msg: CharSequence, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, msg, duration).show()

inline fun toast(msg: CharSequence, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(AppContext.current, msg, duration).show()

inline fun Context.longToast(msg: CharSequence) =
    toast(msg, Toast.LENGTH_LONG)

inline fun longToast(msg: CharSequence) =
    toast(msg, Toast.LENGTH_LONG)