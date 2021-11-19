package com.github.techisfun.android.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.work.Data
import androidx.work.WorkInfo

import androidx.work.WorkManager
import com.github.techisfun.android.workmanager.workers.PeriodicWorker
import timber.log.Timber
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var workerStateTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        workerStateTextView = findViewById(R.id.worker_state)

        findViewById<View>(R.id.btn_start_periodic_worker).setOnClickListener {
            val uuid = PeriodicWorker.start(applicationContext)
            observeWorkResult(uuid)
        }

        findViewById<View>(R.id.btn_stop_periodic_worker).setOnClickListener {
            PeriodicWorker.cancel(applicationContext)
        }
    }

    private fun observeWorkResult(uuid: UUID) {
        WorkManager.getInstance(applicationContext)
            .getWorkInfoByIdLiveData(uuid)
            .observe(this) { workInfo ->
                onWorkInfoUpdateReceived(workInfo)
            }
    }

    private fun onWorkInfoUpdateReceived(workInfo: WorkInfo?) {
        val progressPercent: Int = workInfo?.progress?.getInt(PeriodicWorker.WORK_PROGRESS_KEY, 0) ?: 0
        val state: String = workInfo?.state.toString()
        workerStateTextView.text = getString(
            R.string.worker_state,
            state,
            progressPercent.toString(),
            Date().toString()
        )
    }
}
