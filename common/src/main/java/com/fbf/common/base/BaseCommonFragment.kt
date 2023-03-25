package com.fbf.common.base

import android.media.MediaPlayer
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseCommonFragment : Fragment(), View.OnClickListener {
    lateinit var soundClick:MediaPlayer
}