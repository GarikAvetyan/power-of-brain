package ui

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.R
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.fbf.common.base.BaseCommonActivity
import com.fbf.powerofbrain.databinding.ActivityMainBinding
import com.google.android.gms.ads.MobileAds
import ui.splash.fragment.SplashFragment

class MainActivity : BaseCommonActivity() {
    private lateinit var binding: ActivityMainBinding
    private val splashFragment = SplashFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        backButtonBlock()

        //Full Screen
        val windowInsetsController =
            WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())

        MobileAds.initialize(this) {}

        supportFragmentManager.beginTransaction()
            .replace(binding.mainFrameLayout.id, splashFragment)
            .addToBackStack("")
            .commit()
    }
}