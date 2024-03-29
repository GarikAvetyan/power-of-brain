package ui.memory.fragment

import android.animation.ObjectAnimator
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import application.MyApplication
import com.fbf.common.base.BaseCommonFragment
import com.fbf.powerofbrain.R
import com.fbf.powerofbrain.databinding.FragmentMemoryBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ui.lose.fragment.LoseFragment
import ui.memory.model.MemoryGame
import util.Constants
import util.Preferance

class MemoryFragment : BaseCommonFragment() {
    private lateinit var binding: FragmentMemoryBinding
    private lateinit var points: List<TextView>
    private lateinit var memoryGame: MemoryGame
    private var currentTextSize = 32f
    private var sequence = 0
    private var isStart = false
    private var millishInFuture: Long = 3200
    private var countDownInterval: Long = 100
    private var minusSize: Float = 1f
    private var timer = timer()
    private val basePoint = ArrayList<Int>()
    private val timerWinAnimation = object : CountDownTimer(1000, 1000) {
        override fun onTick(remaining: Long) {
            binding.winLottieAnimationView.visibility = View.VISIBLE
            binding.winLottieAnimationView.playAnimation()
            pointsEnabled(false)
        }

        override fun onFinish() {
            binding.winLottieAnimationView.visibility = View.INVISIBLE
            binding.winLottieAnimationView.cancelAnimation()
            binding.scoreTextView.text = memoryGame.score.toString()
            clearPoints()
            sequence = 0
            memoryGame.restartNumbers()
            memoryGame.getavailablePointsIndexiesRandom()
            setNumbers()
            filterPoitns()
            timer.cancel()
            timer = timer()
            pointsEnabled(false)
            timer.start()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMemoryBinding.inflate(layoutInflater)
        val view = binding.root

        soundClick =
            MediaPlayer.create(requireContext(), R.raw.sound_click)
        soundEnable = Preferance.getBooleanPreferance(requireActivity(), Constants.SOUND_ENABLE)

        memoryGame = MemoryGame()
        binding.winLottieAnimationView.visibility = View.INVISIBLE

        points = listOf(
            binding.point1TextView,
            binding.point2TextView,
            binding.point3TextView,
            binding.point4TextView,
            binding.point5TextView,
            binding.point6TextView,
            binding.point7TextView,
            binding.point8TextView,
            binding.point9TextView,
            binding.point10TextView,
            binding.point11TextView,
            binding.point12TextView,
            binding.point13TextView,
            binding.point14TextView,
            binding.point15TextView,
            binding.point16TextView,
            binding.point17TextView,
            binding.point18TextView,
            binding.point19TextView,
            binding.point20TextView,
            binding.point21TextView,
            binding.point22TextView,
            binding.point23TextView,
            binding.point24TextView,
        )

        setNumbers()
        filterPoitns()

        currentTextSize = 32f

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        descriptionOpenX()

        binding.backImageButton.setOnClickListener(this)
        binding.point1TextView.setOnClickListener(this)
        binding.point2TextView.setOnClickListener(this)
        binding.point3TextView.setOnClickListener(this)
        binding.point4TextView.setOnClickListener(this)
        binding.point5TextView.setOnClickListener(this)
        binding.point6TextView.setOnClickListener(this)
        binding.point7TextView.setOnClickListener(this)
        binding.point8TextView.setOnClickListener(this)
        binding.point9TextView.setOnClickListener(this)
        binding.point10TextView.setOnClickListener(this)
        binding.point11TextView.setOnClickListener(this)
        binding.point12TextView.setOnClickListener(this)
        binding.point13TextView.setOnClickListener(this)
        binding.point14TextView.setOnClickListener(this)
        binding.point15TextView.setOnClickListener(this)
        binding.point16TextView.setOnClickListener(this)
        binding.point17TextView.setOnClickListener(this)
        binding.point18TextView.setOnClickListener(this)
        binding.point19TextView.setOnClickListener(this)
        binding.point20TextView.setOnClickListener(this)
        binding.point21TextView.setOnClickListener(this)
        binding.point22TextView.setOnClickListener(this)
        binding.point23TextView.setOnClickListener(this)
        binding.point24TextView.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.backImageButton -> {
                millishInFuture = 3200
                countDownInterval = 100
                minusSize = 1f
                if (soundEnable) {
                    soundClick.start()
                }
                isStart = false
                timer.cancel()
                timerWinAnimation.cancel()
                requireActivity().supportFragmentManager.apply {
                    beginTransaction().remove(this@MemoryFragment)
                        .commit()
                    popBackStack()
                }
            }

            binding.point1TextView -> {
                check(binding.point1TextView)
            }

            binding.point2TextView -> {
                check(binding.point2TextView)
            }

            binding.point3TextView -> {
                check(binding.point3TextView)
            }

            binding.point4TextView -> {
                check(binding.point4TextView)
            }

            binding.point5TextView -> {
                check(binding.point5TextView)
            }

            binding.point6TextView -> {
                check(binding.point6TextView)
            }

            binding.point7TextView -> {
                check(binding.point7TextView)
            }

            binding.point8TextView -> {
                check(binding.point8TextView)
            }

            binding.point9TextView -> {
                check(binding.point9TextView)
            }

            binding.point10TextView -> {
                check(binding.point10TextView)
            }

            binding.point11TextView -> {
                check(binding.point11TextView)
            }

            binding.point12TextView -> {
                check(binding.point12TextView)
            }

            binding.point13TextView -> {
                check(binding.point13TextView)
            }

            binding.point14TextView -> {
                check(binding.point14TextView)
            }

            binding.point15TextView -> {
                check(binding.point15TextView)
            }

            binding.point16TextView -> {
                check(binding.point16TextView)
            }

            binding.point17TextView -> {
                check(binding.point17TextView)
            }

            binding.point18TextView -> {
                check(binding.point18TextView)
            }

            binding.point19TextView -> {
                check(binding.point19TextView)
            }

            binding.point20TextView -> {
                check(binding.point20TextView)
            }

            binding.point21TextView -> {
                check(binding.point21TextView)
            }

            binding.point22TextView -> {
                check(binding.point22TextView)
            }

            binding.point23TextView -> {
                check(binding.point23TextView)
            }

            binding.point24TextView -> {
                check(binding.point24TextView)
            }
        }
    }

    //When win work this method
    private fun win() {
        if (sequence == memoryGame.availablePointsIndexies.size) {
            if (!isStart) {
                descriptionHideX()
                isStart = true
            }

            memoryGame.score++
            binding.scoreTextView.text = memoryGame.score.toString()
            timerWinAnimation.start()

            when (memoryGame.score) {
                6 -> {
                    millishInFuture = 5000
                    countDownInterval = 100
                    minusSize = 0.64f
                }

                8 -> {
                    millishInFuture = 6400
                    countDownInterval = 100
                    minusSize = 0.5f
                }

                12 -> {
                    millishInFuture = 12800
                    countDownInterval = 100
                    minusSize = 0.25f
                }

                18 -> {
                    millishInFuture = 16000
                    countDownInterval = 100
                    minusSize = 0.2f
                }
            }
        }
    }

    //When lose work this method
    @OptIn(DelicateCoroutinesApi::class)
    private fun lose() {
        isStart = false
        timer.cancel()
        timerWinAnimation.cancel()
        millishInFuture = 3200
        countDownInterval = 100
        minusSize = 1f
        clearPoints()
        sequence = 0
        val loseFragment = LoseFragment()

        GlobalScope.launch {
            if (memoryGame.score > MyApplication.database.bestScoresDao()
                    .getBestScores()[0].memoryBestScores
            ) {
                val bestScores = MyApplication.database.bestScoresDao().getBestScores()[0]
                bestScores.memoryBestScores = memoryGame.score
                MyApplication.database.bestScoresDao().update(bestScores)
            }
        }

        val bundle = Bundle()
        bundle.putInt("score", memoryGame.score)
        loseFragment.arguments = bundle
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrameLayout, loseFragment)
            .addToBackStack(Constants.MEMORY)
            .commit()
    }

    private fun check(textView: TextView) {
        if (basePoint[sequence] == points.indexOf(textView)) {
            textView.visibility = View.INVISIBLE
            sequence++
            win()
        } else {
            lose()
        }
    }

    private fun setNumbers() {
        for (i in memoryGame.availablePointsIndexies.indices) {
            val number = i + 1
            points[memoryGame.availablePointsIndexies[i]].text = (number).toString()
            basePoint.add(memoryGame.availablePointsIndexies[i])
        }
    }

    private fun filterPoitns() {
        for (i in points) {
            if (i.text.isEmpty()) {
                i.visibility = View.INVISIBLE
            } else {
                i.visibility = View.VISIBLE
            }
        }
    }

    private fun clearPoints() {
        for (i in points) {
            i.text = ""
        }
        basePoint.clear()
    }

    private fun hideNumbers() {
        for (i in basePoint) {
            points[i].text = ""
        }
    }

    private fun timer(): CountDownTimer {
        return object : CountDownTimer(millishInFuture, countDownInterval) {
            override fun onTick(remaining: Long) {
                currentTextSize -= minusSize
                for (i in basePoint) {
                    points[i].textSize = currentTextSize
                }
            }

            override fun onFinish() {
                pointsEnabled(true)
                hideNumbers()
                currentTextSize = 32f
                for (i in basePoint) {
                    points[i].textSize = currentTextSize
                }
            }
        }
    }

    private fun pointsEnabled(state: Boolean) {
        if (state) {
            for (i in points) {
                i.isEnabled = true
            }
        } else {
            for (i in points) {
                i.isEnabled = false
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