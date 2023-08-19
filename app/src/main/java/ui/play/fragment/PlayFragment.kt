package ui.play.fragment

import android.annotation.SuppressLint
import android.content.res.Resources
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fbf.common.base.BaseCommonFragment
import com.fbf.powerofbrain.R
import com.fbf.powerofbrain.databinding.FragmentPlayBinding
import ui.action.fragment.ActionFragment
import ui.math.fragment.MathFragment
import ui.memory.fragment.MemoryFragment
import ui.patience.fragment.PatienceFragment
import ui.speed.fragment.SpeedFragment
import ui.vision.fragment.VisionFragment
import util.Constants
import util.Preferance

class PlayFragment : BaseCommonFragment() {
    private lateinit var binding: FragmentPlayBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayBinding.inflate(layoutInflater)
        val view = binding.root

        binding.scrollHandLottieAnimationView.playAnimation()
        binding.mathAnimation.playAnimation()
        binding.speedAnimation.playAnimation()
        binding.patienceAnimation.playAnimation()
        binding.actionAnimation.playAnimation()
        binding.visionAnimation.playAnimation()
        binding.memoryAnimation.playAnimation()

        soundClick =
            MediaPlayer.create(requireContext(), R.raw.sound_click)
        soundEnable = Preferance.getBooleanPreferance(requireActivity(), Constants.SOUND_ENABLE)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backImageButton.setOnClickListener(this)
        binding.mathConstraintLayout.setOnClickListener(this)
        binding.actionConstraintLayout.setOnClickListener(this)
        binding.memoryConstraintLayout.setOnClickListener(this)
        binding.visionConstraintLayout.setOnClickListener(this)
        binding.patienceConstraintLayout.setOnClickListener(this)
        binding.speedConstraintLayout.setOnClickListener(this)

        binding.scrollViewPlay.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            val totalHeight = binding.scrollViewPlay.getChildAt(0).height
            val currentScrollHeight = binding.scrollViewPlay.height + scrollY
            if (totalHeight <= currentScrollHeight) {
                binding.scrollHandLottieAnimationView.visibility = View.INVISIBLE
            } else {
                binding.scrollHandLottieAnimationView.visibility = View.VISIBLE
            }
        }
    }

    override fun onClick(view: View?) {
        if (soundEnable) {
            soundClick.start()
        }
        when (view) {
            binding.backImageButton -> {
                requireActivity().supportFragmentManager.apply {
                    beginTransaction().remove(this@PlayFragment)
                        .commit()
                    popBackStack()
                }
            }
            binding.mathConstraintLayout -> {
                val mathFragment = MathFragment()
                replaceFragment(mathFragment)
            }
            binding.actionConstraintLayout -> {
                val actionFragment = ActionFragment()
                replaceFragment(actionFragment)
            }
            binding.memoryConstraintLayout -> {
                val memoryFragment = MemoryFragment()
                replaceFragment(memoryFragment)
            }
            binding.visionConstraintLayout -> {
                val visionFragment = VisionFragment()
                replaceFragment(visionFragment)
            }
            binding.patienceConstraintLayout -> {
                val patienceFragment = PatienceFragment()
                replaceFragment(patienceFragment)
            }
            binding.speedConstraintLayout -> {
                val speedFragment = SpeedFragment()
                replaceFragment(speedFragment)
            }
        }
    }

    private fun replaceFragment(fragment: BaseCommonFragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrameLayout, fragment)
            .addToBackStack("")
            .commit()
    }
}