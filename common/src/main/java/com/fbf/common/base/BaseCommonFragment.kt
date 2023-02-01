package com.fbf.common.base

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment

open class BaseCommonFragment : Fragment() {
    fun backButtonBlock() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}