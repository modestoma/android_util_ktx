package me.modesto.utils

import java.io.DataOutputStream

private val LINE_SEP by lazy { System.lineSeparator() }

/**
 * get system property value
 *
 * @param propertyKey [String] property key
 * @param isSuper     [Boolean] is super user
 * @author Created by Modesto in 2022/4/16
 */
fun getSystemProperty(propertyKey: String, isSuper: Boolean = false): String {
    return execCommand("getprop $propertyKey", isSuper = isSuper, isNeedResult = true).output.ifBlank { "Null" }
}

/**
 * set system property
 *
 * @param propertyKey   [String] property key
 * @param propertyValue [String] property value
 * @param isSuper       [Boolean] is super user
 * @author Created by Modesto in 2022/4/16
 */
fun setSystemProperty(propertyKey: String, propertyValue: String, isSuper: Boolean = false): Boolean {
    return execCommand("setprop $propertyKey $propertyValue", isSuper = isSuper).isSuccess
}

/**
 * execute shell command, and return command result
 *
 * @param commands     [Array]<[String]> the commands you want to execute
 * @param isSuper      [Boolean] true if you want to execute the commands as super user
 * @param isNeedResult [Boolean] true if you want to get the result of the commands
 * @author Created by Modesto in 2022/4/16
 */
fun execCommand(vararg commands: String,
                isSuper: Boolean = false,
                isNeedResult: Boolean = false): CommandResult {
    var resultCode = -1
    var output = ""
    var error = ""
    if (commands.isEmpty()) {
        return CommandResult(resultCode, output, error)
    }
    var process: Process? = null
    try {
        process = Runtime.getRuntime().exec(if (isSuper) "su" else "sh")
        DataOutputStream(process!!.outputStream).use { os ->
            commands.forEach { command ->
                os.write(command.toByteArray())
                os.writeBytes(LINE_SEP)
                os.flush()
            }
            os.writeBytes("exit$LINE_SEP")
            os.flush()
            resultCode = process.waitFor()
            if (isNeedResult) {
                output = process.inputStream.bufferedReader().readText()
                error = process.errorStream.bufferedReader().readText()
            }
        }
    } finally {
        process?.destroy()
    }
    return CommandResult(resultCode, output, error)
}

/**
 * Command execution result
 *
 * @property resultCode [Int] result code
 * @property output     [String] output
 * @property error      [String] error
 * @author Created by Modesto in 2022/4/16
 */
data class CommandResult(val resultCode: Int,
                         val output: String,
                         val error: String) {
    val isSuccess: Boolean = resultCode == 0
}