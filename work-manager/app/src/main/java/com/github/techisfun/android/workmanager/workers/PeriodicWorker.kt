package com.github.techisfun.android.workmanager.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import timber.log.Timber
import java.time.Duration
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * A simple periodic worker that is executed every 15 minutes
 *
 * @author Andrea Maglie
 */
@HiltWorker
class PeriodicWorker @AssistedInject constructor(@Assisted appContext: Context, @Assisted workerParams: WorkerParameters):
    CoroutineWorker(appContext, workerParams) {

    companion object {
        private const val WORK_UNIQUE_NAME = "periodic-worker"
        const val WORK_PROGRESS_KEY = "progress"

        fun start(appContext: Context): UUID {
            val constraints = Constraints.Builder()
                .setRequiresCharging(false)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val workRequest = PeriodicWorkRequestBuilder<PeriodicWorker>(15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()

            val workManager = WorkManager.getInstance(appContext)
            workManager.enqueueUniquePeriodicWork(WORK_UNIQUE_NAME, ExistingPeriodicWorkPolicy.REPLACE, workRequest)
            Timber.v("Work enqueued")

            return workRequest.id
        }

        fun cancel(context: Context) {
            Timber.v("cancel worker")
            WorkManager
                .getInstance(context)
                .cancelUniqueWork(WORK_UNIQUE_NAME)
        }

        fun restart(context: Context) {
            Timber.v("restarting")
            start(context)
        }
    }

    override suspend fun doWork(): Result {
        return try {

            Timber.d("Execute work here")

            for (i in 0 until 100) {
                setProgress(workDataOf(WORK_PROGRESS_KEY to i))
                delay(250)
            }

            Result.success(workDataOf(WORK_PROGRESS_KEY to 100))
        } catch (e: Exception) {
            Timber.w(e)
            Result.retry()
        }
    }
}
