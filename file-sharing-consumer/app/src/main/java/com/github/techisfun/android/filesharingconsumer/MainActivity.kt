package com.github.techisfun.android.filesharingconsumer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import java.io.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        intent.data?.also { fileUri ->
            try {
                val inputFileDescriptor = contentResolver.openFileDescriptor(fileUri, MODE_READ_WRITE)
                if (inputFileDescriptor == null) {
                    Log.e(TAG, "inputFileDescriptor is null.")
                    return
                }
                val fileConsumer = FileConsumer()
                fileConsumer.consume(inputFileDescriptor)
                renderFileContent(fileConsumer.fileContent)
                setResult(RESULT_OK)
            } catch (e: FileNotFoundException) {
                Log.e(TAG, "File not found.", e)
                return
            }
        }
    }

    private fun renderFileContent(content: String) {
        findViewById<TextView>(R.id.file_content)?.let {
            it.text = content
        }
    }
}
