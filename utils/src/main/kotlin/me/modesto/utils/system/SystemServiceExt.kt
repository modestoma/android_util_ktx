package me.modesto.utils.system

import android.content.Context
import androidx.annotation.MainThread

/**
 * Description.
 *
 * @author Created by Modesto in 2022/4/21
 */
@MainThread
inline fun <reified S> Context.services(): Lazy<S> {
    return lazy { getSystemService(S::class.java) }
}