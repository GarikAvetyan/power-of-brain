package ui.action.fragment

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import application.MyApplication
import com.fbf.common.base.BaseCommonFragment
import com.fbf.powerofbrain.R
import com.fbf.powerofbrain.databinding.FragmentActionBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ui.action.model.ActionGame
import ui.lose.fragment.LoseFragment

class ActionFragment : BaseCommonFragment() {
    private lateinit var binding: FragmentActionBinding
    private lateinit var actionGame: ActionGame
    private lateinit var tiles: List<ImageView>
    private var millisinFuture = 11000L
    private var timer = timer()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentActionBinding.inflate(layoutInflater)
        val view = binding.root

        tiles = listOf(
            binding.tile1ImageView,
            binding.tile2ImageView,
            binding.tile3ImageView,
            binding.tile4ImageView,
            binding.tile5ImageView,
            binding.tile6ImageView,
            binding.tile7ImageView,
            binding.tile8ImageView,
            binding.tile9ImageView,
            binding.tile10ImageView,
            binding.tile11ImageView,
            binding.tile12ImageView,
            binding.tile13ImageView,
            binding.tile14ImageView,
            binding.tile15ImageView,
            binding.tile16ImageView,
        )

        backButtonBlock()
        actionGame = ActionGame()

        for (i in tiles.indices) {
            if (actionGame.tiles[i].black) {
                tiles[i].setBackgroundResource(R.drawable.shape_button_black)
            } else {
                tiles[i].setBackgroundResource(R.drawable.shape_button_white)
            }
        }

        binding.progressBar.apply {
            progress = actionGame.currentScore
            max = actionGame.maxScore
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backImageButton.setOnClickListener(this)
        binding.tile1ImageView.setOnClickListener(this)
        binding.tile2ImageView.setOnClickListener(this)
        binding.tile3ImageView.setOnClickListener(this)
        binding.tile4ImageView.setOnClickListener(this)
        binding.tile5ImageView.setOnClickListener(this)
        binding.tile6ImageView.setOnClickListener(this)
        binding.tile7ImageView.setOnClickListener(this)
        binding.tile8ImageView.setOnClickListener(this)
        binding.tile9ImageView.setOnClickListener(this)
        binding.tile10ImageView.setOnClickListener(this)
        binding.tile11ImageView.setOnClickListener(this)
        binding.tile12ImageView.setOnClickListener(this)
        binding.tile13ImageView.setOnClickListener(this)
        binding.tile14ImageView.setOnClickListener(this)
        binding.tile15ImageView.setOnClickListener(this)
        binding.tile16ImageView.setOnClickListener(this)
        binding.tableConstraintLayout.setOnClickListener(this)

        for(i in tiles){
            rotationOpenY(i)
        }
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.backImageButton -> {
                timer.cancel()
                requireActivity().supportFragmentManager.apply {
                    beginTransaction().remove(this@ActionFragment)
                        .commit()
                    popBackStack()
                    actionGame.clear()
                }
            }
            binding.tableConstraintLayout -> {
                lose()
            }
            binding.tile1ImageView -> {
                if (actionGame.tiles[0].black) {
                    win(0)
                } else {
                    lose()
                }
            }
            binding.tile2ImageView -> {
                if (actionGame.tiles[1].black) {
                    win(1)
                } else {
                    lose()
                }
            }
            binding.tile3ImageView -> {
                if (actionGame.tiles[2].black) {
                    win(2)
                } else {
                    lose()
                }
            }
            binding.tile4ImageView -> {
                if (actionGame.tiles[3].black) {
                    win(3)
                } else {
                    lose()
                }
            }
            binding.tile5ImageView -> {
                if (actionGame.tiles[4].black) {
                    win(4)
                } else {
                    lose()
                }
            }
            binding.tile6ImageView -> {
                if (actionGame.tiles[5].black) {
                    win(5)
                } else {
                    lose()
                }
            }
            binding.tile7ImageView -> {
                if (actionGame.tiles[6].black) {
                    win(6)
                } else {
                    lose()
                }
            }
            binding.tile8ImageView -> {
                if (actionGame.tiles[7].black) {
                    win(7)
                } else {
                    lose()
                }
            }
            binding.tile9ImageView -> {
                if (actionGame.tiles[8].black) {
                    win(8)
                } else {
                    lose()
                }
            }
            binding.tile10ImageView -> {
                if (actionGame.tiles[9].black) {
                    win(9)
                } else {
                    lose()
                }
            }
            binding.tile11ImageView -> {
                if (actionGame.tiles[10].black) {
                    win(10)
                } else {
                    lose()
                }
            }
            binding.tile12ImageView -> {
                if (actionGame.tiles[11].black) {
                    win(11)
                }
            }
            binding.tile13ImageView -> {
                if (actionGame.tiles[12].black) {
                    win(12)
                } else {
                    lose()
                }
            }
            binding.tile14ImageView -> {
                if (actionGame.tiles[13].black) {
                    win(13)
                } else {
                    lose()
                }
            }
            binding.tile15ImageView -> {
                if (actionGame.tiles[14].black) {
                    win(14)
                } else {
                    lose()
                }
            }
            binding.tile16ImageView -> {
                if (actionGame.tiles[15].black) {
                    win(15)
                } else {
                    lose()
                }
            }
        }
    }

    //When win work this method
    private fun win(index: Int) {
        actionGame.score++
        binding.scoreTextView.text = actionGame.score.toString()
        actionGame.currentScore++
        binding.progressBar.progress = actionGame.currentScore

        if (actionGame.score == 1) {
            timer.start()
        }

        if (binding.progressBar.progress == binding.progressBar.max) {
            timer.cancel()
            timer.start()
            actionGame.currentScore = 0
            binding.progressBar.progress = actionGame.currentScore
            actionGame.maxScore += 20
            binding.progressBar.max = actionGame.maxScore
        }

        actionGame.changeTile(index)

        for (i in tiles.indices) {
            if (actionGame.tiles[i].black) {
                tiles[i].setBackgroundResource(R.drawable.shape_button_black)
            } else {
                tiles[i].setBackgroundResource(R.drawable.shape_button_white)
            }
        }
    }

    //When lose work this method
    @OptIn(DelicateCoroutinesApi::class)
    private fun lose() {
        timer.cancel()
        actionGame.clear()
        val loseFragment = LoseFragment()

        GlobalScope.launch {
            if (actionGame.score > MyApplication.database.bestScoresDao()
                    .getBestScores()[0].actionBestScores
            ) {
                val bestScores = MyApplication.database.bestScoresDao().getBestScores()[0]
                bestScores.actionBestScores = actionGame.score
                MyApplication.database.bestScoresDao().update(bestScores)
            }
        }

        val bundle = Bundle()
        bundle.putInt("score", actionGame.score)
        loseFragment.arguments = bundle
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrameLayout, loseFragment)
            .addToBackStack("Action")
            .commit()

        binding.progressBar.progress = 0
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

    override fun onDestroyView() {
        super.onDestroyView()
        actionGame.clear()
    }

    private fun rotationOpenY(view: View) {
        ObjectAnimator.ofFloat(view, View.ROTATION_Y, 270f, 360f).apply {
            duration = 500
            start()
        }
    }



}