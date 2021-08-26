package com.github.techisfun.android.workmanager

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.work.ListenableWorker.Result
import androidx.work.testing.TestListenableWorkerBuilder
import androidx.work.workDataOf
import com.github.techisfun.android.workmanager.workers.OneTimeWithRetryWorker
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
class OneTimeWithRetryWorkerTest {

    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun workWithTwoRetriesShouldNotFail() {
        val data = workDataOf("force-work-failure" to true)

        val worker = TestListenableWorkerBuilder<OneTimeWithRetryWorker>(context)
            .setInputData(data)
            .setRunAttemptCount(2)
            .build()

        val result = worker.startWork().get()

        assertThat(result, `is`(Result.retry()))
    }
    @Test
    fun workWithThreeRetriesShouldFail() {
        val data = workDataOf("force-work-failure" to true)

        val worker = TestListenableWorkerBuilder<OneTimeWithRetryWorker>(context)
            .setInputData(data)
            .setRunAttemptCount(3)
            .build()

        val result = worker.startWork().get()

        assertThat(result, `is`(Result.failure()))
    }
}
