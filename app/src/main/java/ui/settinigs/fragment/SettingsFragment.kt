package ui.settinigs.fragment

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fbf.common.base.BaseCommonFragment
import com.fbf.powerofbrain.R
import com.fbf.powerofbrain.databinding.FragmentSettingsBinding
import util.Constants
import util.Preferance

class SettingsFragment : BaseCommonFragment() {
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        val view = binding.root

        binding.settingsAnimation.playAnimation()

        soundClick =
            MediaPlayer.create(requireContext(), R.raw.sound_click)
        soundEnable = Preferance.getBooleanPreferance(requireActivity(), Constants.SOUND_ENABLE)

        if (soundEnable) {
            binding.voiceImageView.setImageResource(R.drawable.item_volume)
            binding.voiceTextView.text = requireActivity().getString(R.string.volume_status_on)
        } else {
            binding.voiceImageView.setImageResource(R.drawable.item_no_volume)
            binding.voiceTextView.text = requireActivity().getString(R.string.volume_status_off)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backImageButton.setOnClickListener(this)
        binding.voiceConstraintLayout.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.backImageButton -> {
                if (soundEnable) {
                    soundClick.start()
                }
                requireActivity().supportFragmentManager.apply {
                    beginTransaction().remove(this@SettingsFragment)
                        .commit()
                    popBackStack()
                }
            }

            binding.voiceConstraintLayout -> {

                Preferance.setBooleanPreferance(
                    requireActivity(), Constants.SOUND_ENABLE, !soundEnable
                )

                soundEnable =
                    Preferance.getBooleanPreferance(requireActivity(), Constants.SOUND_ENABLE)

                binding.voiceImageView.setImageResource(
                    if (soundEnable) {
                        binding.voiceTextView.text =
                            requireActivity().getString(R.string.volume_status_on)
                        R.drawable.item_volume
                    } else {
                        binding.voiceTextView.text =
                            requireActivity().getString(R.string.volume_status_off)
                        R.drawable.item_no_volume
                    }
                )
            }
        }
    }
}