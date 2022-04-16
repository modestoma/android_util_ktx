package me.modesto.utils

import android.content.Context

/**
 * Description.
 *
 * @author Created by Modesto in 2022/4/16
 */
@Suppress("StaticFieldLeak")
object AppContext {

    @JvmStatic
    lateinit var current: Context

    internal fun init(context: Context) {
        current = context
    }

}