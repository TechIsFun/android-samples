package com.github.techisfun.android.workmanager.chain

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.github.techisfun.android.workmanager.thenMaybe
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber
import java.util.*

/**
 * An example of how to chain multiple workers
 *
 * @author Andrea Maglie
 */
fun enqueueWorkerChain(context: Context, applyOptionalWorker: Boolean) {
    WorkManager.getInstance(context)
        .beginUniqueWork(
            "chain",
            ExistingWorkPolicy.REPLACE,
            OneTimeWorkRequest.from(ChainFirstWorker::class.java)
        )
        .then(OneTimeWorkRequest.from(ChainSecondWorker::class.java))
        .thenMaybe<ChainOptionalWorker>(applyOptionalWorker)
        .enqueue()
}


@HiltWorker
class ChainFirstWorker @AssistedInject constructor(@Assisted appContext: Context, @Assisted workerParams: WorkerParameters):
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {

            Timber.d("Execute work here")


            Result.success()
        } catch (e: Exception) {
            Timber.w(e)
            Result.failure()
        }
    }
}

@HiltWorker
class ChainSecondWorker @AssistedInject constructor(@Assisted appContext: Context, @Assisted workerParams: WorkerParameters):
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {

            Timber.d("Execute work here")

            Result.success()
        } catch (e: Exception) {
            Timber.w(e)
            Result.failure()
        }
    }
}

@HiltWorker
class ChainOptionalWorker @AssistedInject constructor(@Assisted appContext: Context, @Assisted workerParams: WorkerParameters):
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {

            Timber.d("Execute work here")

            Result.success()
        } catch (e: Exception) {
            Timber.w(e)
            Result.failure()
        }
    }
}
