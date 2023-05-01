package ui.settinigs.fragment

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fbf.common.base.BaseCommonFragment
import com.fbf.powerofbrain.R
import com.fbf.powerofbrain.databinding.FragmentSettingsBinding

class SettingsFragment : BaseCommonFragment() {
    private lateinit var binding:FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        val view = binding.root

        binding.settingsAnimation.playAnimation()

        soundClick =
            MediaPlayer.create(requireContext(), R.raw.sound_click)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backImageButton.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.backImageButton -> {
                soundClick.start()
                requireActivity().supportFragmentManager.apply {
                    beginTransaction().remove(this@SettingsFragment)
                        .commit()
                    popBackStack()
                }
            }
        }
    }
}