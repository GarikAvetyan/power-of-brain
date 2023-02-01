package ui

import android.os.Bundle
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.fbf.common.base.BaseCommonActivity
import com.fbf.powerofbrain.databinding.ActivityMainBinding
import ui.splash.fragment.SplashFragment

class MainActivity : BaseCommonActivity() {
    private lateinit var binding: ActivityMainBinding
    private val splashFragment = SplashFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Full Screen
        val windowInsetsController =
            WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())

        supportFragmentManager.beginTransaction()
            .replace(binding.mainFrameLayout.id, splashFragment)
            .addToBackStack("")
            .commit()
    }
}