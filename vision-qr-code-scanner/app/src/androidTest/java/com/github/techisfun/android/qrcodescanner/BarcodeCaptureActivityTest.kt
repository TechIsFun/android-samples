package com.github.techisfun.android.qrcodescanner

import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.GrantPermissionRule
import com.github.techisfun.android.qrcodescanner.BarcodeCaptureActivity.Companion.ANIMATION_ENABLED
import com.github.techisfun.android.qrcodescanner.BarcodeCaptureActivity.Companion.FLASH_ENABLED
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class BarcodeCaptureActivityTest {

    @get:Rule
    val rule: ActivityScenarioRule<BarcodeCaptureActivity> = ActivityScenarioRule(BarcodeCaptureActivity::class.java, bundleOf(ANIMATION_ENABLED to false, FLASH_ENABLED to false))

    @get:Rule
    val cameraPermissionRule: GrantPermissionRule = GrantPermissionRule.grant(android.Manifest.permission.CAMERA)

    @Test
    fun startBarcodeCaptureActivity() {
        val scenario = rule.scenario
        scenario.onActivity { activity ->
            assertNotNull(activity)
        }

        Thread.sleep(2000)

        scenario.moveToState(Lifecycle.State.DESTROYED)
    }
}
