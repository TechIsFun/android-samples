package com.github.techisfun.android.workmanager

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * This custom application class is required to setup HiltWorkerFactory.
 * Please check also "Remove WorkManagerInitializer node" in AndroidManifest.xml
 *
 * @author Andrea Maglie
 */
@HiltAndroidApp
class CustomApplication: Application(), androidx.work.Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    /**
     * https://developer.android.com/training/dependency-injection/hilt-jetpack#workmanager
     */
    override fun getWorkManagerConfiguration() =
        androidx.work.Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}
