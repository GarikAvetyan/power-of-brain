package ui.math.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import application.MyApplication
import com.fbf.common.base.BaseCommonFragment
import com.fbf.powerofbrain.R
import com.fbf.powerofbrain.databinding.FragmentMathBinding
import com.fbf.room.bestscores.model.BestScores
import kotlinx.coroutines.*
import ui.lose.fragment.LoseFragment
import ui.math.model.MathGame

class MathFragment : BaseCommonFragment() {
    private lateinit var binding: FragmentMathBinding
    private lateinit var mathGame: MathGame
    private var millisinFuture = 6000L
    private var timer = timer()

    private val timerWinGif = object : CountDownTimer(1000, 1000) {
        override fun onTick(remaining: Long) {
            binding.winGif.visibility = View.VISIBLE
            binding.expression1TextView.isClickable = false
            binding.expression2TextView.isClickable = false
        }

        override fun onFinish() {
            binding.winGif.visibility = View.INVISIBLE
            binding.expression1TextView.isClickable = true
            binding.expression2TextView.isClickable = true
            mathGame.changeNumbers()
            expressionTextViewsChange()
            timer = timer()
            timer.start()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMathBinding.inflate(layoutInflater)
        val view = binding.root
        millisinFuture = 6000L
        mathGame = MathGame()

        backButtonBlock()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backImageButton.setOnClickListener(this)
        binding.expression1TextView.setOnClickListener(this)
        binding.expression2TextView.setOnClickListener(this)

        expressionTextViewsChange()
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.backImageButton -> {
                timer.cancel()
                timerWinGif.cancel()
                requireActivity().supportFragmentManager.apply {
                    beginTransaction().remove(this@MathFragment)
                        .commit()
                    popBackStack()
                }
            }
            binding.expression1TextView -> {
                if (mathGame.answer1 >= mathGame.answer2) {
                    win()
                } else {
                    lose()
                }
            }
            binding.expression2TextView -> {
                if (mathGame.answer2 >= mathGame.answer1) {
                    win()
                } else {
                    lose()
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun expressionTextViewsChange() {
        binding.expression1TextView.text =
            "${mathGame.number1} ${mathGame.operator1} ${mathGame.number2}"
        binding.expression2TextView.text =
            "${mathGame.number3} ${mathGame.operator2} ${mathGame.number4}"
    }

    //When lose work this method
    @OptIn(DelicateCoroutinesApi::class)
    private fun lose() {
        timer.cancel()

        val loseFragment = LoseFragment()

        GlobalScope.launch {
            if (mathGame.score > MyApplication.database.bestScoresDao()
                    .getBestScores()[0].mathBestScores
            ) {
                val bestScores = MyApplication.database.bestScoresDao().getBestScores()[0]
                bestScores.mathBestScores = mathGame.score
                MyApplication.database.bestScoresDao().update(bestScores)
            }
        }

        val bundle = Bundle()
        bundle.putInt("score", mathGame.score)
        loseFragment.arguments = bundle
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrameLayout, loseFragment)
            .addToBackStack("Math")
            .commit()
    }

    //When win work this method
    private fun win() {
        mathGame.score++
        binding.scoreTextView.text = mathGame.score.toString()
        timer.cancel()
        when (mathGame.score) {
            5 -> {
                millisinFuture = 5000L
            }
            15 -> {
                millisinFuture = 4000L
            }
            20 -> {
                millisinFuture = 3000L
            }
        }
        timerWinGif.start()
    }

    private fun timer(): CountDownTimer {
        return object : CountDownTimer(millisinFuture, 1000) {
            override fun onTick(remaining: Long) {
                binding.winGif.visibility = View.INVISIBLE
                binding.timeTextView.text = (remaining / 1000).toString()
            }

            override fun onFinish() {
                lose()
            }
        }
    }
}