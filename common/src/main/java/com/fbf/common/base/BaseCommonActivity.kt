package com.fbf.common.base

import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity

open class BaseCommonActivity : AppCompatActivity() {
    fun backButtonBlock() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        }

        onBackPressedDispatcher.addCallback(this, callback)
    }
}