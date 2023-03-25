package ui.patience.fragment

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import application.MyApplication
import com.fbf.common.base.BaseCommonFragment
import com.fbf.powerofbrain.R
import com.fbf.powerofbrain.databinding.FragmentPatienceBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ui.lose.fragment.LoseFragment
import ui.patience.model.PatienceGame
import util.Constants


class PatienceFragment : BaseCommonFragment(), View.OnTouchListener {
    private lateinit var binding: FragmentPatienceBinding
    private lateinit var patienceGame: PatienceGame
    private var isStart = false
    private val frequency = object : CountDownTimer(1000000, 1600) {
        override fun onTick(remaining: Long) {
            if (patienceGame.initialCoordinate == patienceGame.left || patienceGame.initialCoordinate == patienceGame.right) {
                binding.bait.animate()
                    .translationX(patienceGame.initialCoordinate)
                binding.bait.animate().translationY(patienceGame.initialDeviation)
                binding.bait.animate().duration = 0

                play()

            } else {
                binding.bait.animate()
                    .translationY(patienceGame.initialCoordinate)
                binding.bait.animate().translationX(patienceGame.initialDeviation)
                binding.bait.animate().duration = 0

                play()

            }
        }

        override fun onFinish() {
            win()
        }

    }

    private val timer = object : CountDownTimer(1000000, 100) {
        override fun onTick(remaining: Long) {
            patienceGame.score += 0.1
            binding.timerTextView.text = (Math.round(patienceGame.score * 100.0) / 100.0).toString()
        }

        override fun onFinish() {
            win()
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPatienceBinding.inflate(layoutInflater)
        val view = binding.root
        isStart = false

        soundClick =
            MediaPlayer.create(requireContext(), R.raw.sound_click)

        patienceGame = PatienceGame()

        return view
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        descriptionOpenX()
        binding.backImageButton.setOnClickListener(this)
        binding.bait.setOnTouchListener(this)
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.backImageButton -> {
                soundClick.start()
                timer.cancel()
                frequency.cancel()
                isStart = false
                requireActivity().supportFragmentManager.apply {
                    beginTransaction().remove(this@PatienceFragment)
                        .commit()
                    popBackStack()
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(view: View?, p1: MotionEvent?): Boolean {
        when (view) {
            binding.bait -> {
                if (isStart) {
                    win()
                } else {
                    isStart = true
                    descriptionHideX()
                    timer.start()
                    frequency.start()
                }
            }
        }
        return false
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun win() {
        timer.cancel()
        frequency.cancel()
        isStart = false
        val loseFragment = LoseFragment()
        val score: Double = Math.round(patienceGame.score * 100.0) / 100.0

        GlobalScope.launch {


            if (score < MyApplication.database.bestScoresDao()
                    .getBestScores()[0].patienceBestScores
                && MyApplication.database.bestScoresDao()
                    .getBestScores()[0].patienceBestScores != 0.0
            ) {
                val bestScores = MyApplication.database.bestScoresDao().getBestScores()[0]
                bestScores.patienceBestScores = score
                MyApplication.database.bestScoresDao().update(bestScores)
            } else if (MyApplication.database.bestScoresDao()
                    .getBestScores()[0].patienceBestScores == 0.0
            ) {
                val bestScores = MyApplication.database.bestScoresDao().getBestScores()[0]
                bestScores.patienceBestScores = score
                MyApplication.database.bestScoresDao().update(bestScores)
            }
        }

        val bundle = Bundle()
        bundle.putDouble("score", score)
        loseFragment.arguments = bundle
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrameLayout, loseFragment)
            .addToBackStack(Constants.PATIENCE)
            .commit()
    }

    private fun play() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (patienceGame.endpointCoordinate == patienceGame.left || patienceGame.endpointCoordinate == patienceGame.right) {
                binding.bait.animate()
                    .translationX(patienceGame.endpointCoordinate)
                binding.bait.animate().translationY(patienceGame.endpointDeviation)
                binding.bait.animate().duration = 700
            } else {
                binding.bait.animate()
                    .translationY(patienceGame.endpointCoordinate)
                binding.bait.animate().translationX(patienceGame.endpointDeviation)
                binding.bait.animate().duration = 700
            }
            patienceGame.update()
        }, 400)
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