@file:Suppress("unused", "NOTHING_TO_INLINE")

package me.modesto.utils.hint

import android.util.Log
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import me.modesto.utils.AppContext
import me.modesto.utils.file.ensureFileExists
import me.modesto.utils.hint.ILogger.Companion.printer
import me.modesto.utils.hint.ILogger.Companion.save
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Android Log wrapper
 *
 * @author Created by Modesto in 2022/4/16
 */
///////////////////////////////////////////////////////////////////////////
// Constant
///////////////////////////////////////////////////////////////////////////
private var ENABLE_LOG = true
private var ENABLE_SAVE_TO_FILE = false
var DEFAULT_TAG = "Logger"
private val PACKAGE_NAME by lazy { AppContext.current.packageName }
private val LOG_LEVEL_NAME by lazy { charArrayOf('V', 'D', 'I', 'W', 'E', 'A') }

///////////////////////////////////////////////////////////////////////////
// API
///////////////////////////////////////////////////////////////////////////
/**
 * Logger 初始化函数
 *
 * @param enable     [Boolean] 是否启用日志输出
 * @param defaultTag [String] 指定默认日志 Tag
 * @param printer    [ILoggerPrinter] 指定自定义的日志输出逻辑
 * @author Created by Luqian Ma in 2022/5/23
 */
internal fun internalInitLogger(enable: Boolean,
               defaultTag: String? = null,
               printer: ((level: LogLevel, tag: String, msg: Any?, throwable: Throwable?) -> Unit)? = null) {
    ENABLE_LOG = enable
    defaultTag?.let { DEFAULT_TAG = defaultTag }
    printer?.let {
        ILogger.printer = object : ILoggerPrinter {
            override fun println(level: LogLevel, tag: String, msg: Any?, throwable: Throwable?) =
                printer.invoke(level, tag, msg, throwable)
        }
    }
}

internal fun internalInitLoggerSave(save2file: Boolean,
                   saveFile: File? = null,
                   save: ((level: LogLevel, tag: String, msg: Any?, throwable: Throwable?) -> Unit)? = null) {
    ENABLE_SAVE_TO_FILE = save2file
    saveFile?.let { ILoggerSave.file = saveFile }
    if (null == save) {
        ILogger.save = DefaultSave()
    } else {
        ILogger.save = object : ILoggerSave {
            override fun save(level: LogLevel, tag: String, msg: Any?, throwable: Throwable?) {
                save.invoke(level, tag, msg, throwable)
            }
        }
    }
}

/**
 * 获取 ILogger 对象
 *
 * @param tag [String] 指定的日志 Tag
 * @author Created by Luqian Ma in 2022/5/23
 */
fun logger(tag: String) = object : ILogger {
    override val tag: String get() = tag
}

inline fun logVerbose(msg: Any?, throwable: Throwable? = null) = log(LogLevel.VERBOSE, DEFAULT_TAG, msg, throwable)

inline fun logDebug(msg: Any?, throwable: Throwable? = null) = log(LogLevel.DEBUG, DEFAULT_TAG, msg, throwable)

inline fun logInfo(msg: Any?, throwable: Throwable? = null) = log(LogLevel.INFO, DEFAULT_TAG, msg, throwable)

inline fun logWarn(msg: Any?, throwable: Throwable? = null) = log(LogLevel.WARN, DEFAULT_TAG, msg, throwable)

inline fun logError(msg: Any?, throwable: Throwable? = null) = log(LogLevel.ERROR, DEFAULT_TAG, msg, throwable)

inline fun logAssert(msg: Any?, throwable: Throwable? = null) = log(LogLevel.ASSERT, DEFAULT_TAG, msg, throwable)

fun log(level: LogLevel, tag: String, msg: Any?, throwable: Throwable? = null) {
    printer.println(level, tag, msg, throwable)
}

fun ILogger.logVerbose(msg: Any?, throwable: Throwable? = null) = printer.println(LogLevel.VERBOSE, tag, msg, throwable)

fun ILogger.logDebug(msg: Any?, throwable: Throwable? = null) = printer.println(LogLevel.DEBUG, tag, msg, throwable)

fun ILogger.logInfo(msg: Any?, throwable: Throwable? = null) = printer.println(LogLevel.INFO, tag, msg, throwable)

fun ILogger.logWarn(msg: Any?, throwable: Throwable? = null) = printer.println(LogLevel.WARN, tag, msg, throwable)

fun ILogger.logError(msg: Any?, throwable: Throwable? = null) = printer.println(LogLevel.ERROR, tag, msg, throwable)

fun ILogger.logAssert(msg: Any?, throwable: Throwable? = null) = printer.println(LogLevel.ASSERT, tag, msg, throwable)

///////////////////////////////////////////////////////////////////////////
// Impl
///////////////////////////////////////////////////////////////////////////
@JvmInline
value class LogLevel private constructor(val value: Int) {
    companion object {
        val VERBOSE = LogLevel(Log.VERBOSE)
        val DEBUG = LogLevel(Log.DEBUG)
        val INFO = LogLevel(Log.INFO)
        val WARN = LogLevel(Log.WARN)
        val ERROR = LogLevel(Log.ERROR)
        val ASSERT = LogLevel(Log.ASSERT)
    }
}

internal class DefaultPrinter : ILoggerPrinter {
    override fun println(level: LogLevel, tag: String, msg: Any?, throwable: Throwable?) {
        if (ENABLE_LOG) Log.println(level.value, tag, "${msg.toString()}\n${Log.getStackTraceString(throwable)}")
        if (ENABLE_SAVE_TO_FILE) save?.save(level, tag, msg, throwable)
    }
}

internal class DefaultSave : ILoggerSave {

    private val simpleDataFormatter by lazy { SimpleDateFormat("yyyy_MM_dd HH:mm:ss.SSS ", Locale.ENGLISH) }
    private val clock by lazy { Clock.System }

    override fun save(level: LogLevel, tag: String, msg: Any?, throwable: Throwable?) {
        val date = clock.now().toLocalDateTime(TimeZone.UTC)
        val throwableContent = if (null != throwable) Log.getStackTraceString(throwable) else ""
        ILoggerSave.file.appendText("$date $PACKAGE_NAME [${getLevelName(level)}] [${tag}]: ${msg.toString()}${throwableContent}\n")
    }
}

///////////////////////////////////////////////////////////////////////////
// 接口类
///////////////////////////////////////////////////////////////////////////
/**
 * Logger 接口类
 *
 * @property printer Logger 的输出类
 * @author Created by Luqian Ma in 2022/5/23
 */
interface ILogger {

    val tag: String get() = TAG

    companion object {
        var save: ILoggerSave? = null
        var printer: ILoggerPrinter = DefaultPrinter()
    }

}

/**
 * Logger 内容输出的接口类
 *
 * @author Created by Luqian Ma in 2022/5/23
 */
interface ILoggerPrinter {
    /**
     * 日志内容输出实现
     *
     * @param
     * @author Created by Luqian Ma in 2022/5/23
     */
    fun println(level: LogLevel, tag: String, msg: Any?, throwable: Throwable? = null)
}

/**
 * Logger 内容保存的接口类
 *
 * @author Created by Luqian Ma in 2022/5/23
 */
interface ILoggerSave {
    companion object {
        var file: File = File(getDefaultLogPath())
    }

    fun save(level: LogLevel, tag: String, msg: Any?, throwable: Throwable? = null)
}

///////////////////////////////////////////////////////////////////////////
// 拓展
///////////////////////////////////////////////////////////////////////////
val Any.TAG: String
    get() = javaClass.simpleName

/**
 * get current stack index
 *
 * @param trace [Array]<[StackTraceElement]> stack trace
 * @author Created by Modesto in 2022/4/16
 */
private fun getStackIndex(trace: Array<StackTraceElement>): Int {
    var index = 3
    while (index < trace.size) {
        val name = trace[index].className
        if (ILogger::class.java.name != name) return --index
        index++
    }
    return -1
}

private fun getDefaultLogPath(): String {
    val date = SimpleDateFormat("yyyy_MM_dd", Locale.ENGLISH).format(Date())
    val path = "${AppContext.current.cacheDir.absolutePath}/log/${date}.txt"
    path.ensureFileExists()
    Log.d("MLQ", "path: $path")
    return path
}

private inline fun getLevelName(level: LogLevel): Char {
    return LOG_LEVEL_NAME[level.value - LogLevel.VERBOSE.value]
}