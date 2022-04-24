package me.modesto.utils

import android.content.Context
import me.modesto.utils.hint.Logger

/**
 * Description.
 *
 * @author Created by Modesto in 2022/4/16
 */
object Utils {

    @JvmStatic
    @JvmOverloads
    fun init(context: Context, loggerConfig: Logger.LoggerConfig.() -> Unit = {}) {
        AppContext.init(context)
        loggerConfig.invoke(Logger.init())
    }

}