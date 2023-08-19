package ui.speed.fragment

import android.animation.ObjectAnimator
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import application.MyApplication
import com.fbf.common.base.BaseCommonFragment
import com.fbf.powerofbrain.R
import com.fbf.powerofbrain.databinding.FragmentSpeedBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ui.lose.fragment.LoseFragment
import ui.speed.model.SpeedGame
import util.Constants
import util.Preferance


class SpeedFragment : BaseCommonFragment() {
    private lateinit var binding: FragmentSpeedBinding
    private lateinit var speedGame: SpeedGame
    private var millisinFuture = 11000L
    private var playMillisinFuture = 1000000L
    private var timer = timer()
    private val play = play()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSpeedBinding.inflate(layoutInflater)
        val view = binding.root

        soundClick =
            MediaPlayer.create(requireContext(), R.raw.sound_click)
        soundEnable = Preferance.getBooleanPreferance(requireActivity(), Constants.SOUND_ENABLE)

        speedGame = SpeedGame()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        descriptionOpenX()

        binding.backImageButton.setOnClickListener(this)
        binding.grayButton.setOnClickListener(this)

        binding.speedGameProgressBar.apply {
            progress = speedGame.currentScore
            max = speedGame.maxScore
        }
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.backImageButton -> {
                if (soundEnable) {
                    soundClick.start()
                }
                timer.cancel()
                play.cancel()
                requireActivity().supportFragmentManager.apply {
                    beginTransaction().remove(this@SpeedFragment)
                        .commit()
                    popBackStack()
                }
            }

            binding.grayButton -> {
                win()
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun lose() {
        timer.cancel()
        play.cancel()

        val loseFragment = LoseFragment()

        GlobalScope.launch {
            if (speedGame.score > MyApplication.database.bestScoresDao()
                    .getBestScores()[0].speedBestScores
            ) {
                val bestScores = MyApplication.database.bestScoresDao().getBestScores()[0]
                bestScores.speedBestScores = speedGame.score
                MyApplication.database.bestScoresDao().update(bestScores)
            }
        }

        val bundle = Bundle()
        bundle.putInt("score", speedGame.score)
        loseFragment.arguments = bundle
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrameLayout, loseFragment)
            .addToBackStack(Constants.SPEED)
            .commit()

        binding.speedGameProgressBar.progress = 0
    }

    private fun win() {
        speedGame.score++
        binding.scoreTextView.text = speedGame.score.toString()
        speedGame.currentScore++
        binding.speedGameProgressBar.progress = speedGame.currentScore

        if (speedGame.score == 1) {
            descriptionHideX()
            binding.grayButton.animate()
                .translationX(speedGame.end)
            binding.grayButton.animate().duration = 1000

            speedGame.update()
            timer.start()

            Handler(Looper.getMainLooper()).postDelayed({
                play.start()
            }, 1250)
        }

        if (binding.speedGameProgressBar.progress == binding.speedGameProgressBar.max) {
            timer.cancel()
            timer.start()
            speedGame.currentScore = 0
            binding.speedGameProgressBar.progress = speedGame.currentScore
            speedGame.maxScore += 10
            binding.speedGameProgressBar.max = speedGame.maxScore
        }
    }

    private fun timer(): CountDownTimer {
        return object : CountDownTimer(millisinFuture, 1000) {
            override fun onTick(remaining: Long) {
                binding.timeTextView.text = (remaining / 1000).toString()
            }

            override fun onFinish() {
                lose()
            }
        }
    }

    private fun play(): CountDownTimer {
        return object : CountDownTimer(playMillisinFuture, 2500) {
            override fun onTick(remaining: Long) {

                binding.grayButton.animate()
                    .translationX(speedGame.start)
                binding.grayButton.animate().translationY(speedGame.deviation)
                binding.grayButton.animate().duration = 0

                Handler(Looper.getMainLooper()).postDelayed({
                    binding.grayButton.animate()
                        .translationX(speedGame.end)
                    binding.grayButton.animate().translationY(speedGame.deviation)
                    binding.grayButton.animate().duration = 2000

                    speedGame.update()
                }, 250)
            }

            override fun onFinish() {
                lose()
            }

        }
    }

    private fun descriptionOpenX() {
        ObjectAnimator.ofFloat(binding.descriptionTextView, View.ROTATION_X, 270f, 360f).apply {
            duration = 500
            start()
        }
    }

    private fun descriptionHideX() {
        ObjectAnimator.ofFloat(binding.descriptionTextView, View.ROTATION_X, 0f, 90f).apply {
            duration = 500
            start()
        }
    }
}