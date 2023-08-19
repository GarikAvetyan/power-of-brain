package ui.home.fragment

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fbf.common.base.BaseCommonFragment
import com.fbf.powerofbrain.R
import com.fbf.powerofbrain.databinding.FragmentHomeBinding
import ui.awards.fragment.AwardsFragment
import ui.play.fragment.PlayFragment
import ui.settinigs.fragment.SettingsFragment
import util.Constants
import util.Preferance

class HomeFragment : BaseCommonFragment() {
    private lateinit var binding: FragmentHomeBinding
    private val playFragment = PlayFragment()
    private val settingsFragment = SettingsFragment()
    private val awardsFragment = AwardsFragment()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        val view = binding.root

        soundClick =
            MediaPlayer.create(requireContext(), R.raw.sound_click)
        soundEnable = Preferance.getBooleanPreferance(requireActivity(), Constants.SOUND_ENABLE)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.playButton.setOnClickListener(this)
        binding.settingsImageView.setOnClickListener(this)
        binding.awardsImageView.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (soundEnable) {
            soundClick.start()
        }
        when (view) {
            binding.playButton -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.mainFrameLayout, playFragment)
                    .addToBackStack("")
                    .commit()
            }

            binding.settingsImageView -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.mainFrameLayout, settingsFragment)
                    .addToBackStack("")
                    .commit()
            }

            binding.awardsImageView -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.mainFrameLayout, awardsFragment)
                    .addToBackStack("")
                    .commit()
            }
        }
    }

}