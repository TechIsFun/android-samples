package com.github.techisfun.lottiesample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lottieFragment = LottieFragment.newInstance(R.raw.signature, getString(R.string.main_message))
        supportFragmentManager.beginTransaction()
            .add(R.id.container, lottieFragment)
            .commit()
    }
}