package com.fbf.common.base

import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment

abstract class BaseCommonFragment : Fragment(), View.OnClickListener {
    fun backButtonBlock() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}