package com.github.techisfun.android.workmanager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber
import java.lang.Exception
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * A periodic worker that is executed at fixed hours
 *
 * @author Andrea Maglie
 */
@HiltWorker
class PeriodicAtFixedTimeWorker @AssistedInject constructor(@Assisted appContext: Context, @Assisted workerParams: WorkerParameters):
    CoroutineWorker(appContext, workerParams) {

    companion object {
        private const val WORK_UNIQUE_NAME = "periodic-worker-at-fixed-hours"

        // execute at every hour from 8 am to 6 pm
        val ENABLED_HOURS = 8..18

        @Suppress("MemberVisibilityCanBePrivate")
        fun start(appContext: Context): UUID {
            val constraints = Constraints.Builder()
                .setRequiresCharging(false)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val intervalInMinutes = if (BuildConfig.DEBUG) {
                5L
            } else {
                60L
            }

            val initialDelay = if (BuildConfig.DEBUG) {
                0
            } else {
                calcMinutesUntilNextHour()
            }

            val workRequest = PeriodicWorkRequestBuilder<PeriodicAtFixedTimeWorker>(intervalInMinutes, TimeUnit.MINUTES)
                .setInitialDelay(initialDelay, TimeUnit.MINUTES)
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

        private fun calcMinutesUntilNextHour(): Long {
            val calendar = Calendar.getInstance()
            val currentMinute = calendar[Calendar.MINUTE]
            return (60 - currentMinute).toLong()
        }

    }

    override suspend fun doWork(): Result {
        return try {
            val calendar = GregorianCalendar.getInstance()
            val currentHour = calendar[Calendar.HOUR_OF_DAY]
            if (ENABLED_HOURS.contains(currentHour)) {
                Timber.d("Execute work here")
            }
            Result.success()
        } catch (e: Exception) {
            Timber.w(e)
            Result.retry()
        }
    }

}
