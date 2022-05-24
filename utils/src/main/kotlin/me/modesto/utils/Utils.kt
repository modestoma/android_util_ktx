package me.modesto.utils

import android.content.Context
import me.modesto.utils.hint.ILoggerPrinter
import me.modesto.utils.hint.LogLevel
import me.modesto.utils.hint.internalInitLogger
import me.modesto.utils.hint.internalInitLoggerSave
import java.io.File

/**
 * Description.
 *
 * @author Created by Modesto in 2022/4/16
 */
object Utils {

    fun init(context: Context): Utils {
        AppContext.init(context)
        return this
    }

    /**
     * Logger 初始化函数
     *
     * @param enable     [Boolean] 是否启用日志输出
     * @param defaultTag [String] 指定默认日志 Tag
     * @param printer    [ILoggerPrinter] 指定自定义的日志输出逻辑
     * @author Created by Luqian Ma in 2022/5/23
     */
    fun initLogger(enable: Boolean,
                   defaultTag: String? = null,
                   printer: ((level: LogLevel, tag: String, msg: Any?, throwable: Throwable?) -> Unit)? = null): Utils {
        internalInitLogger(enable, defaultTag, printer)
        return this
    }

    fun initLoggerSave(save2file: Boolean,
                       saveFile: File? = null,
                       save: ((level: LogLevel, tag: String, msg: Any?, throwable: Throwable?) -> Unit)? = null): Utils {
        internalInitLoggerSave(save2file, saveFile, save)
        return this
    }

}