package ui.splash.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fbf.common.base.BaseCommonFragment
import com.fbf.powerofbrain.R
import com.fbf.powerofbrain.databinding.FragmentSplashBinding
import ui.home.fragment.HomeFragment


class SplashFragment : BaseCommonFragment() {
    private lateinit var binding: FragmentSplashBinding
    private val homeFragment = HomeFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(layoutInflater)
        val view = binding.root

        Handler(Looper.getMainLooper()).postDelayed({
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.mainFrameLayout, homeFragment)
                .remove(this)
                .commit()
        }, 5000)

        return view
    }
}