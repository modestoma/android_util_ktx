package me.modesto.utils.hint

import android.util.Log
import me.modesto.utils.AppContext
import me.modesto.utils.file.ensureFileExists
import me.modesto.utils.file.write2File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors

/**
 * Android Log wrapper
 *
 * @author Created by Modesto in 2022/4/16
 */
object Logger {

    ///////////////////////////////////////////////////////////////////////////
    // Logger global variables
    ///////////////////////////////////////////////////////////////////////////

    private val LINE_SEP by lazy { System.lineSeparator() }
    private val FILE_SEP by lazy { System.getProperty("file.separator") }

    private val config = LoggerConfig()
    private val executor by lazy { Executors.newSingleThreadExecutor() }
    private val simpleDataFormatter by lazy { SimpleDateFormat("yyyy_MM_dd HH:mm:ss.SSS ", Locale.getDefault()) }
    private val LEVEL by lazy { charArrayOf('V', 'D', 'I', 'W', 'E', 'A') }

    ///////////////////////////////////////////////////////////////////////////
    // Logger info border
    ///////////////////////////////////////////////////////////////////////////

    private const val SEP = "  "
    private const val TOP_LEFT_CORNER = "╔"
    private const val BOTTOM_LEFT_CORNER = "╚"
    private const val MIDDLE_CORNER = "╟"
    private const val HORIZONTAL_DOUBLE_LINE = "║"
    private const val DOUBLE_DIVIDER = "════════════════════════════════════════"
    private const val SINGLE_DIVIDER = "────────────────────────────────────────"
    private const val TOP_BORDER = TOP_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER
    private const val BOTTOM_BORDER = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER
    private const val MIDDLE_BORDER = MIDDLE_CORNER + SINGLE_DIVIDER + SINGLE_DIVIDER

    ///////////////////////////////////////////////////////////////////////////
    // Logger interface
    ///////////////////////////////////////////////////////////////////////////

    /**
     * [Log.VERBOSE] level log
     *
     * @param tag      [String]          custom tag
     * @param contents [Array]<[String]> log contents
     * @author Created by Modesto in 2022/4/16
     */
    @JvmStatic
    fun vTag(tag: String, vararg contents: String) = print(Log.VERBOSE, tag, *contents)

    @JvmStatic
    fun v(vararg contents: String) = vTag(config.defaultTag, *contents)

    @JvmStatic
    fun vDetailTag(tag: String, vararg contents: String) = printDetail(Log.VERBOSE, tag, *contents)

    @JvmStatic
    fun vDetail(vararg contents: String) = vDetailTag(config.defaultTag, *contents)

    /**
     * [Log.DEBUG] level log
     *
     * @param tag      [String]          custom tag
     * @param contents [Array]<[String]> log contents
     * @author Created by Modesto in 2022/4/16
     */
    @JvmStatic
    fun dTag(tag: String, vararg contents: String) = print(Log.DEBUG, tag, *contents)

    @JvmStatic
    fun d(vararg contents: String) = dTag(config.defaultTag, *contents)

    @JvmStatic
    fun dDetailTag(tag: String, vararg contents: String) = printDetail(Log.DEBUG, tag, *contents)

    @JvmStatic
    fun dDetail(vararg contents: String) = dDetailTag(config.defaultTag, *contents)

    /**
     * [Log.INFO] level log
     *
     * @param tag      [String]          custom tag
     * @param contents [Array]<[String]> log contents
     * @author Created by Modesto in 2022/4/16
     */
    @JvmStatic
    fun iTag(tag: String, vararg contents: String) = print(Log.INFO, tag, *contents)

    @JvmStatic
    fun i(vararg contents: String) = iTag(config.defaultTag, *contents)

    @JvmStatic
    fun iDetailTag(tag: String, vararg contents: String) = printDetail(Log.INFO, tag, *contents)

    @JvmStatic
    fun iDetail(vararg contents: String) = iDetailTag(config.defaultTag, *contents)

    /**
     * [Log.WARN] level log
     *
     * @param tag      [String]          custom tag
     * @param contents [Array]<[String]> log contents
     * @author Created by Modesto in 2022/4/16
     */
    @JvmStatic
    fun wTag(tag: String, vararg contents: String) = print(Log.WARN, tag, *contents)

    @JvmStatic
    fun w(vararg contents: String) = wTag(config.defaultTag, *contents)

    @JvmStatic
    fun wDetailTag(tag: String, vararg contents: String) = printDetail(Log.WARN, tag, *contents)

    @JvmStatic
    fun wDetail(vararg contents: String) = wDetailTag(config.defaultTag, *contents)

    /**
     * [Log.ERROR] level log
     *
     * @param tag      [String]          custom tag
     * @param contents [Array]<[String]> log contents
     * @author Created by Modesto in 2022/4/16
     */
    @JvmStatic
    fun eTag(tag: String, vararg contents: String) = print(Log.ERROR, tag, *contents)

    @JvmStatic
    fun e(vararg contents: String) = eTag(config.defaultTag, *contents)

    @JvmStatic
    fun eDetailTag(tag: String, vararg contents: String) = printDetail(Log.ERROR, tag, *contents)

    @JvmStatic
    fun eDetail(vararg contents: String) = eDetailTag(config.defaultTag, *contents)

    /**
     * [Log.ASSERT] level log
     *
     * @param tag      [String]          custom tag
     * @param contents [Array]<[String]> log contents
     * @author Created by Modesto in 2022/4/16
     */
    @JvmStatic
    fun aTag(tag: String, vararg contents: String) = print(Log.ASSERT, tag, *contents)

    @JvmStatic
    fun a(vararg contents: String) = aTag(config.defaultTag, *contents)

    @JvmStatic
    fun aDetailTag(tag: String, vararg contents: String) = printDetail(Log.ASSERT, tag, *contents)

    @JvmStatic
    fun aDetail(vararg contents: String) = aDetailTag(config.defaultTag, *contents)

    ///////////////////////////////////////////////////////////////////////////
    // Logger print
    ///////////////////////////////////////////////////////////////////////////

    /**
     * output log
     *
     * @param level    [String]          log level
     * @param tag      [String]          log tag
     * @param contents [Array]<[String]> log contents
     * @author Created by Modesto in 2022/4/16
     */
    private fun print(level: Int, tag: String, vararg contents: String) =
        contents.forEach { content -> print(level, tag, content) }

    /**
     * output log
     *
     * @param level   [Int]    log level
     * @param tag     [String] log tag
     * @param content [String] log content
     * @author Created by Modesto in 2022/4/16
     */
    private fun print(level: Int, tag: String, content: String) {
        if (!config.enable) return
        Log.println(level, tag, content)
        if (config.write2File) executor.execute { print2File(level, tag, content) }
    }

    /**
     * save log to file
     *
     * @param level   [Int]    log level
     * @param tag     [String] log tag
     * @param content [String] log content
     * @author Created by Modesto in 2022/4/16
     */
    private fun print2File(level: Int, tag: String, content: String) {
        val format = simpleDataFormatter.format(Date())
        val date = format.substring(0, 10)
        val logFilePath = currentLogFilePath(date)
        if (!logFilePath.ensureFileExists()) return
        val log = "$format ${LEVEL[level - Log.VERBOSE]}/[$tag]: $content$LINE_SEP"
        logFilePath.write2File(content = log)
    }

    /**
     * output detail log
     *
     * @param level    [String]          log level
     * @param tag      [String]          log tag
     * @param contents [Array]<[String]> log contents
     * @author Created by Modesto in 2022/4/16
     */
    private fun printDetail(level: Int, tag: String, vararg contents: String) {
        if (!config.enable) return
        val trace = Thread.currentThread().stackTrace
        val stackIndex = getStackIndex(trace)
        val className = trace[stackIndex].fileName
        val methodName = trace[stackIndex].methodName
        val lineNum = trace[stackIndex].lineNumber
        print(level, tag, TOP_BORDER)
        print(level, tag, "$HORIZONTAL_DOUBLE_LINE${SEP}Thread: ${Thread.currentThread().name}")
        print(level, tag, MIDDLE_BORDER)
        print(level, tag, "$HORIZONTAL_DOUBLE_LINE$SEP($className:$lineNum).$methodName")
        print(level, tag, MIDDLE_BORDER)
        contents.forEach { content -> print(level, tag, "$HORIZONTAL_DOUBLE_LINE$SEP$content") }
        print(level, tag, BOTTOM_BORDER)
    }

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
            if (Logger::class.java.name != name) return --index
            index++
        }
        return -1
    }

    ///////////////////////////////////////////////////////////////////////////
    // Logger Configuration
    ///////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun init() = config

    /**
     * get current log file path
     *
     * @author Created by Modesto in 2022/4/16
     */
    private val currentLogFilePath: (date: String) -> String =
        { date -> "${config.logDir}${config.logFilePrefix}_$date.${config.logFileExtension}" }

    /**
     * [Logger] Configuration.
     *
     * @property enable           [Boolean] enable log output.
     * @property write2File       [Boolean] true if write log to file.
     * @property logDir           [String]  log directory.
     * @property logFilePrefix    [String]  log file prefix.
     * @property logFileExtension [String]  log file extension.
     * @property defaultTag       [String]  default tag.
     * @author Created by Modesto in 2022/4/16
     */
    data class LoggerConfig(var enable: Boolean = true,
                            var write2File: Boolean = false,
                            var logDir: String = "${AppContext.current.filesDir.path}${FILE_SEP}logs$FILE_SEP",
                            var logFilePrefix: String = "log",
                            var logFileExtension: String = "txt",
                            var defaultTag: String = "Logger")

}

