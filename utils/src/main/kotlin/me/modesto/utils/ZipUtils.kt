package me.modesto.utils

import java.io.File
import java.io.FileInputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

/**
 * Description.
 *
 * @author Created by Modesto in 2022/4/20
 */
fun File.unzipTo(destDir: File) {
}

private fun ZipInputStream.asIterator() = ZipIterator(this)

private class ZipIterator(private val stream: ZipInputStream) : Iterator<ZipEntry> {
    private var next: ZipEntry? = null
    override fun hasNext(): Boolean {
        next = stream.nextEntry
        return null != next
    }

    override fun next() = next ?: throw NoSuchElementException()
}