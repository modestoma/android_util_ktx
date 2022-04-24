package me.modesto.utils.file

import android.net.Uri
import android.os.Environment
import me.modesto.utils.AppContext
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException

/**
 * get the file by path, if the path is null, return null
 *
 * @return [File]
 * @author Created by Modesto in 2022/3/14
 */
fun String?.getFile(): File? = if (this.isNullOrBlank()) null else File(this)

///////////////////////////////////////////////////////////////////////////
// create file or directory
///////////////////////////////////////////////////////////////////////////

/**
 * check the directory is exist, if not, create it
 *
 * @return [Boolean] true if the directory is exist or create successfully, false otherwise
 * @author Created by Modesto in 2022/3/14
 */
fun String?.ensureDirExists(): Boolean = this?.getFile()?.ensureDirExists() ?: false

fun File?.ensureDirExists(): Boolean = null != this && (if (this.exists()) this.isDirectory else this.mkdirs())

/**
 * check the file is exist, if not, create it
 *
 * @return [Boolean] true if the file is exist or create successfully, false otherwise
 * @author Created by Modesto in 2022/3/14
 */
fun String?.ensureFileExists(): Boolean = this?.getFile()?.ensureFileExists() ?: false

fun File?.ensureFileExists(): Boolean {
    if (this?.exists() ?: return false) return true
    if (!this.parentFile.ensureDirExists()) return false
    return try {
        this.createNewFile()
    } catch (e: IOException) {
        e.printStackTrace()
        false
    }
}

///////////////////////////////////////////////////////////////////////////
// Check if a file or directory exists
///////////////////////////////////////////////////////////////////////////

/**
 * check if a file or directory exists
 *
 * @return [Boolean] true if the file is exist, false otherwise
 * @author Created by Modesto in 2022/3/14
 */
fun String?.isFileExists(): Boolean = this?.getFile()?.isFileExists() ?: false

fun File?.isFileExists(): Boolean {
    if (null == this) return false
    return if (this.exists()) {
        true
    } else {
        this.isFileExistsApi29()
    }
}

private fun File.isFileExistsApi29(): Boolean {
    try {
        val uri = Uri.parse(this.path)
        val cr = AppContext.current.contentResolver
        val assertFileDescriptor = cr.openAssetFileDescriptor(uri, "r") ?: return false
        assertFileDescriptor.close()
    } catch (e: Exception) {
        e.printStackTrace()
        return false
    }
    return true
}

///////////////////////////////////////////////////////////////////////////
// delete the file or directory
///////////////////////////////////////////////////////////////////////////

/**
 * delete the file or directory
 *
 * @return [Boolean] true if the file or directory is deleted, false otherwise
 * @author Created by Modesto in 2022/3/14
 */
fun String?.deleteFileOrDir(): Boolean = this?.getFile()?.deleteFileOrDir() ?: false

fun File?.deleteFileOrDir(): Boolean {
    return if (this?.isDirectory ?: return false) {
        this.deleteDir()
    } else {
        this.deleteFile()
    }
}

private fun File.deleteDir(): Boolean {
    if (!this.exists()) return true
    if (!this.isDirectory) return false
    this.listFiles()?.let { files ->
        files.forEach { file ->
            if (file.isFile) {
                if (!file.delete()) return false
            } else if (file.isDirectory) {
                if (!deleteDir()) return false
            }
        }
    }
    return this.delete()
}

private fun File.deleteFile(): Boolean = !this.exists() || this.isFile && this.delete()

///////////////////////////////////////////////////////////////////////////
// move the file or directory
///////////////////////////////////////////////////////////////////////////

/**
 * move the file or directory
 *
 * @param destPath [String] the destination file or directory path
 * @return [Boolean] true if the file or directory is moved, false otherwise
 * @author Created by Modesto in 2022/3/14
 */
fun String?.moveTo(destPath: String?): Boolean = this.getFile().moveTo(destPath.getFile())

fun File?.moveTo(destPath: String?): Boolean = this.moveTo(destPath.getFile())

fun File?.moveTo(dest: File?): Boolean {
    // return if the src is null or does not exist
    if (!(this?.isFileExists() ?: return false)) return false
    // return if the dest is null
    if (null == dest) return false
    return if (this.isDirectory) {
        this.moveDirTo(dest)
    } else {
        this.moveFileTo(dest)
    }
}

private fun File.moveDirTo(dest: File): Boolean {
    if (dest.isFileExists()) {
        if (!dest.isDirectory) return false
        if (!dest.deleteFileOrDir()) return false
    }
    return this.renameTo(dest)
}

private fun File.moveFileTo(dest: File): Boolean {
    if (dest.isFileExists()) {
        if (!dest.isDirectory) return false
        if (!dest.deleteFileOrDir()) return false
    }
    return this.renameTo(dest)
}

///////////////////////////////////////////////////////////////////////////
// copy file or directory
///////////////////////////////////////////////////////////////////////////

/**
 * copy the file or directory
 *
 * @param dest      [File] the destination file or directory
 * @param overwrite [Boolean] true if the destination file or directory should be overwritten, false otherwise
 * @return [Boolean] true if the file or directory is copied, false otherwise
 * @author Created by Modesto in 2022/3/14
 */
fun File?.copy(dest: File?, overwrite: Boolean = true): Boolean {
    // return if the src is null or does not exist
    if (!(this?.isFileExists() ?: return false)) return false
    // return if the dest is null
    if (null == dest) return false
    return if (this.isDirectory) {
        this.copyDir(dest, overwrite)
    } else {
        this.copyFile(dest, overwrite)
    }
}

private fun File.copyDir(dest: File, overwrite: Boolean): Boolean {
    if (dest.isFileExists()) {
        if (!dest.isDirectory) return false
        if (overwrite && !dest.deleteFileOrDir()) return false
    }
    return try {
        this.copyRecursively(dest, overwrite)
    } catch (e: IOException) {
        e.printStackTrace()
        false
    }
}

private fun File.copyFile(dest: File, overwrite: Boolean): Boolean {
    if (dest.isFileExists()) {
        if (!dest.isFile) return false
        if (overwrite && !dest.deleteFileOrDir()) return false
    }
    return try {
        this.copyTo(dest, overwrite).isFileExists()
    } catch (e: IOException) {
        e.printStackTrace()
        false
    }
}

///////////////////////////////////////////////////////////////////////////
// write or read file
///////////////////////////////////////////////////////////////////////////

/**
 * write content to the file
 *
 * @param content  [String] the content to write
 * @param isAppend [Boolean] true if the content should be appended to the file, false otherwise
 * @author Created by Modesto in 2022/3/14
 */
fun String?.write2File(content: String, isAppend: Boolean = true): Boolean = this.getFile().write2File(content, isAppend)

fun File?.write2File(content: String, isAppend: Boolean = true): Boolean {
    if (null == this) return false
    if (!this.ensureFileExists()) return false
    try {
        BufferedWriter(FileWriter(this, isAppend)).use { writer ->
            writer.write(content)
        }
    } catch (e: IOException) {
        e.printStackTrace()
        return false
    }
    return true
}

///////////////////////////////////////////////////////////////////////////
// File path
///////////////////////////////////////////////////////////////////////////
val filesDir: File by lazy { AppContext.current.filesDir }
val cacheDir: File by lazy { AppContext.current.cacheDir }
val rootDir: File by lazy { Environment.getRootDirectory() }
val dataDir: File by lazy { Environment.getDataDirectory() }
val downloadCacheDir: File by lazy { Environment.getDownloadCacheDirectory() }