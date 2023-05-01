package ui.awards.fragment

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import application.MyApplication
import com.fbf.common.base.BaseCommonFragment
import com.fbf.powerofbrain.R
import com.fbf.powerofbrain.databinding.FragmentAwardsBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AwardsFragment : BaseCommonFragment() {

    private lateinit var binding: FragmentAwardsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAwardsBinding.inflate(layoutInflater)
        val view = binding.root

        soundClick =
            MediaPlayer.create(requireContext(), R.raw.sound_click)

        return view
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch {
            binding.mathGameBestScoreNumber.text = MyApplication.database.bestScoresDao()
                .getBestScores()[0].mathBestScores.toString()
            binding.speedGameBestScoreNumber.text = MyApplication.database.bestScoresDao()
                .getBestScores()[0].speedBestScores.toString()
            binding.patienceGameBestScoreNumber.text = MyApplication.database.bestScoresDao()
                .getBestScores()[0].patienceBestScores.toString()
            binding.actionGameBestScoreNumber.text = MyApplication.database.bestScoresDao()
                .getBestScores()[0].actionBestScores.toString()
            binding.visonGameBestScoreNumber.text = MyApplication.database.bestScoresDao()
                .getBestScores()[0].visionBestScores.toString()
            binding.memoryGameBestScoreNumber.text = MyApplication.database.bestScoresDao()
                .getBestScores()[0].memoryBestScores.toString()
        }

        binding.backImageButton.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.backImageButton -> {
                soundClick.start()
                requireActivity().supportFragmentManager.apply {
                    beginTransaction().remove(this@AwardsFragment)
                        .commit()
                    popBackStack()
                }
            }
        }
    }
}