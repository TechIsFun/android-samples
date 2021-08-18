package com.github.techisfun.android.workmanager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * A simple periodic worker that is executed every 2 minutes
 *
 * @author Andrea Maglie
 */
@HiltWorker
class PeriodicWorker @AssistedInject constructor(@Assisted appContext: Context, @Assisted workerParams: WorkerParameters):
    CoroutineWorker(appContext, workerParams) {

    companion object {
        private const val WORK_UNIQUE_NAME = "periodic-worker"

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

            Result.success()
        } catch (e: Exception) {
            Timber.w(e)
            Result.retry()
        }
    }
}
