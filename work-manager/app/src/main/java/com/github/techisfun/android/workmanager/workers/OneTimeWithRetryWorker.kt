package com.github.techisfun.android.workmanager.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber
import java.util.*

/**
 * A simple worker that is executed just one time
 *
 * @author Andrea Maglie
 */
@HiltWorker
class OneTimeWithRetryWorker @AssistedInject constructor(@Assisted appContext: Context, @Assisted workerParams: WorkerParameters):
    CoroutineWorker(appContext, workerParams) {

    companion object {
        private const val WORK_UNIQUE_NAME = "periodic-worker"
        private const val MAX_RETRY_COUNT = 3

        fun start(appContext: Context): UUID {
            val constraints = Constraints.Builder()
                .setRequiresCharging(false)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val workRequest = OneTimeWorkRequestBuilder<OneTimeWithRetryWorker>()
                .setConstraints(constraints)
                // since Android 12 and WorkManager 2.7.0
                .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST) // or: DROP_WORK_REQUEST
                .build()

            val workManager = WorkManager.getInstance(appContext)
            workManager.enqueueUniqueWork(WORK_UNIQUE_NAME, ExistingWorkPolicy.REPLACE, workRequest)
            Timber.v("Work enqueued")

            return workRequest.id
        }
    }

    override suspend fun doWork(): Result {
        return try {

            Timber.d("Execute work here")

            if (inputData.getBoolean("force-work-failure", false)) {
                throw Exception("force-work-failure")
            }

            Result.success()
        } catch (e: Exception) {
            Timber.w(e)
            if (runAttemptCount < MAX_RETRY_COUNT) {
                Result.retry()
            } else {
                Result.failure()
            }
        }
    }
}
