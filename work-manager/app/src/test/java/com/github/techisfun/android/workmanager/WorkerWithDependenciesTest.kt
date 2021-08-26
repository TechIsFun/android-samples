package com.github.techisfun.android.workmanager

import android.content.Context
import androidx.work.*
import com.github.techisfun.android.workmanager.usecases.SampleUseCase
import com.github.techisfun.android.workmanager.workers.WorkerWithDependencies
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

/**
 * @author Andrea Maglie
 */
class WorkerWithDependenciesTest {

    @RelaxedMockK private lateinit var context: Context
    @RelaxedMockK private lateinit var workerParams: WorkerParameters
    @RelaxedMockK private lateinit var sampleUseCase: SampleUseCase
    private lateinit var worker: WorkerWithDependencies

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun doWork() = runBlocking {
        worker = WorkerWithDependencies(context, workerParams, sampleUseCase)

        val result = worker.doWork()

        assertThat(result, `is`(ListenableWorker.Result.success()))
    }

}
