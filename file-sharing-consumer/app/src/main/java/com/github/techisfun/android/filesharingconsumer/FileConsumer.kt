package com.github.techisfun.android.filesharingconsumer

import android.os.ParcelFileDescriptor
import android.util.Log
import java.io.*
import java.lang.Exception
import java.lang.StringBuilder
import java.time.LocalDateTime

/**
 * @author Andrea Maglie
 */
class FileConsumer {
    var fileContent: String = ""
        private set

    fun consume(inputFileDescriptor: ParcelFileDescriptor) {
        val fileDescriptor = inputFileDescriptor.fileDescriptor
        fileContent = readFile(fileDescriptor)
        updateFile(fileDescriptor)
    }

    private fun readFile(fileDescriptor: FileDescriptor): String {
        val r = BufferedReader(InputStreamReader(FileInputStream(fileDescriptor)))
        val content = StringBuilder()
        var line: String?
        while (r.readLine().also { line = it } != null) {
            content.append(line).append('\n')
        }
        return content.toString()
    }

    private fun updateFile(fileDescriptor: FileDescriptor) {
        try {
            val writer = FileWriter(fileDescriptor)
            writer.append("\n")
                .append("Document consumed on: ")
                .append(LocalDateTime.now().toString())
            writer.flush()
            writer.close()
        } catch (e: Exception) {
            Log.e(TAG, "got exception while updating document", e)
        }
    }
}
