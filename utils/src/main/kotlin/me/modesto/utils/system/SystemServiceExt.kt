package me.modesto.utils.system

import android.content.Context
import androidx.annotation.MainThread
import me.modesto.utils.AppContext

/**
 * Description.
 *
 * @author Created by Modesto in 2022/4/21
 */
@MainThread
inline fun <reified S> Context.services(): Lazy<S> {
    return lazy { getSystemService(S::class.java) }
}

@MainThread
inline fun <reified S> services(): Lazy<S> {
    return lazy { AppContext.current.getSystemService(S::class.java) }
}