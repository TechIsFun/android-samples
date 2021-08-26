package com.github.techisfun.android.workmanager

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.work.ListenableWorker.Result
import androidx.work.testing.TestListenableWorkerBuilder
import com.github.techisfun.android.workmanager.workers.PeriodicWorker
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * @author Andrea Maglie
 */
@RunWith(JUnit4::class)
class PeriodicWorkerTest {

    private lateinit var context: Context
    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun doWork() {
        val worker =
            TestListenableWorkerBuilder<PeriodicWorker>(context).build()

        val result = worker.startWork().get()

        assertThat(result, `is`(Result.success()))
    }

}
