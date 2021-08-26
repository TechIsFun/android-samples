package com.github.techisfun.android.workmanager

import android.annotation.SuppressLint
import androidx.work.Data
import androidx.work.ListenableWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkContinuation

/**
 * @author Andrea Maglie
 */

/**
 * Applies a [ListenableWorker] to a [WorkContinuation] in case [apply] is `true`.
 * Source: https://github.com/android/architecture-components-samples/blob/android-s/WorkManagerSample/lib/src/main/java/com/example/background/ImageOperations.kt
 */
inline fun <reified T : ListenableWorker> WorkContinuation.thenMaybe(
    apply: Boolean
): WorkContinuation {
    return if (apply) {
        then(workRequest<T>())
    } else {
        this
    }
}

/**
 * Creates a [OneTimeWorkRequest] with the given inputData and a [tag] if set.
 */
@SuppressLint("UnsafeExperimentalUsageError")
inline fun <reified T : ListenableWorker> workRequest(
    inputData: Data? = null,
    tag: String? = null
) =
    OneTimeWorkRequestBuilder<T>().apply {
        inputData?.let { setInputData(it) }
        //setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
        if (!tag.isNullOrEmpty()) {
            addTag(tag)
        }
    }.build()
