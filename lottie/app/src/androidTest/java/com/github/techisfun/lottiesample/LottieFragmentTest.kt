package com.github.techisfun.lottiesample

import android.widget.TextView
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.airbnb.lottie.LottieAnimationView
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.junit.Test


@RunWith(AndroidJUnit4::class)
class LottieFragmentTest {

    @Test
    fun testLottieFragmentWithParams() {
        val fragmentArgs = LottieFragment.buildArgumentsBundle(R.raw.signature, "Test message")
        val scenario = launchFragmentInContainer<LottieFragment>(fragmentArgs)

        scenario.moveToState(Lifecycle.State.RESUMED)

        scenario.onFragment { fragment ->
            val animationView = fragment.view!!.findViewById<LottieAnimationView>(R.id.animation)
            assertNotNull(animationView)

            val messageView = fragment.view!!.findViewById<TextView>(R.id.message)
            assertEquals("Test message", messageView.text)
        }
    }
}