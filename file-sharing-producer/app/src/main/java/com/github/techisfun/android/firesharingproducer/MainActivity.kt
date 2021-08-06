package com.github.techisfun.android.firesharingproducer

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.FileProvider
import java.lang.Exception
import java.time.LocalDateTime
import android.app.Activity
import android.widget.TextView
import android.widget.Toast

import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import java.io.*
import java.lang.StringBuilder
import java.nio.file.Files

private const val TAG = "FileSharingProducer"

class MainActivity : AppCompatActivity() {

    private val intentLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            renderMessage(getString(R.string.file_consumed))
            readUpdatedDocument()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.send_file_to_consumer).apply {
            setOnClickListener {
                val document = generateDocument()
                sendFileToConsumer(document)
            }
        }
    }

    private fun generateDocument(): File? {
        val documentFile = getDocumentFile()
        val timestamp = LocalDateTime.now().toString()
        val documentContent = "Document generated on $timestamp"

        return try {
            val writer = FileWriter(documentFile)
            writer.write(documentContent)
            writer.flush()
            writer.close()
            documentFile
        } catch (e: Exception) {
            Log.e(TAG, "got exception while document $documentFile", e)
            null
        }
    }

    private fun getDocumentFile(): File {
        val dir = File(filesDir, "documents")
        if (!dir.exists()) {
            dir.mkdir()
        }

        return File(dir, "document.txt")
    }

    private fun readUpdatedDocument() {
        renderFileContent(getDocumentFile())
    }


    private fun sendFileToConsumer(file: File?) {
        if (file == null) return

        try {
            val fileUri: Uri = FileProvider.getUriForFile(
                this@MainActivity,
                FILE_PROVIDER_AUTHORITY,
                file)

            val intent = Intent(INTENT_FILTER_FILE_SHARING_CONSUMER).apply {
                setDataAndType(fileUri, contentResolver.getType(fileUri))
                //addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            }

            intentLauncher.launch(intent)
        } catch (e: Exception) {
            Log.e(TAG, "got exception while sharing: $file", e)
        }
    }

    private fun renderMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun renderFileContent(file: File) {
        val content = readFile(file)
        findViewById<TextView>(R.id.file_content)?.let {
            it.text = content
        }
    }

    private fun readFile(file: File): String {
        val r = BufferedReader(InputStreamReader(FileInputStream(file)))
        val content = StringBuilder()
        var line: String?
        while (r.readLine().also { line = it } != null) {
            content.append(line).append('\n')
        }
        return content.toString()
    }
}
