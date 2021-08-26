package com.github.techisfun.android.workmanager.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.github.techisfun.android.workmanager.usecases.SampleUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber
import java.util.*

/**
 * A worker with injected dependencies.
 * Don't forget to setup HiltWorkerFactory in your Application class!
 *
 * @author Andrea Maglie
 */
@HiltWorker
class WorkerWithDependencies @AssistedInject constructor(@Assisted appContext: Context, @Assisted workerParams: WorkerParameters, private val sampleUseCase: SampleUseCase):
    CoroutineWorker(appContext, workerParams) {

    companion object {
        private const val WORK_UNIQUE_NAME = "work-with-dependencies"

        fun start(appContext: Context): UUID {
            val constraints = Constraints.Builder()
                .setRequiresCharging(false)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val workRequest = OneTimeWorkRequestBuilder<WorkerWithDependencies>()
                .setConstraints(constraints)
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

            sampleUseCase.execute()

            Result.success()
        } catch (e: Exception) {
            Timber.w(e)
            Result.failure()
        }
    }
}
