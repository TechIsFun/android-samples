package com.github.techisfun.lottiesample

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.airbnb.lottie.LottieAnimationView
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val rule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun startMainActivity() {
        val scenario = rule.scenario
        scenario.onActivity { activity ->
            val animationView = activity.findViewById<LottieAnimationView>(R.id.animation)
            assertNotNull(animationView)
            assertTrue(animationView.isAnimating)
        }

        scenario.moveToState(Lifecycle.State.DESTROYED)
    }
}
