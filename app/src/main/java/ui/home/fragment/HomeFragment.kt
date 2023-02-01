package ui.home.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.fbf.common.base.BaseCommonFragment
import com.fbf.powerofbrain.R
import com.fbf.powerofbrain.databinding.FragmentHomeBinding
import ui.play.fragment.PlayFragment

class HomeFragment : BaseCommonFragment(), View.OnClickListener{
    private lateinit var binding: FragmentHomeBinding
    private val playFragment = PlayFragment()


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        val view = binding.root

        binding.playButton.setOnClickListener(this)
        backButtonBlock()

        return view
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.playButton -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.mainFrameLayout, playFragment)
                    .addToBackStack("")
                    .commit()
            }
        }
    }

}