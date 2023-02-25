package ui.vision.fragment

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
import com.fbf.powerofbrain.databinding.FragmentVisionBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ui.lose.fragment.LoseFragment
import ui.vision.model.VisionGame

class VisionFragment : BaseCommonFragment() {
    private lateinit var binding: FragmentVisionBinding
    private lateinit var images: List<ImageView>
    private lateinit var visionGame: VisionGame
    private var millisinFuture = 4000L
    private var timer = timer()
    private val timerWinGif = object : CountDownTimer(1500, 1500) {
        override fun onTick(remaining: Long) {
            binding.winGif.visibility = View.VISIBLE
        }

        override fun onFinish() {
            binding.winGif.visibility = View.INVISIBLE
            buttonsBlock()
            visionGame.updateImages()
            filterImages()
            binding.chooseConstraintLayout.visibility = View.INVISIBLE
            timer = timer()
            timer.start()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVisionBinding.inflate(layoutInflater)
        val view = binding.root
        millisinFuture = 4000L
        backButtonBlock()

        images = listOf(
            binding.imageView1,
            binding.imageView2,
            binding.imageView3,
            binding.imageView4,
            binding.imageView5,
            binding.imageView6,
            binding.imageView7,
            binding.imageView8,
            binding.imageView9,
            binding.imageView10,
            binding.imageView11,
            binding.imageView12,
            binding.imageView13,
            binding.imageView14,
            binding.imageView15,
        )

        visionGame = VisionGame()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backImageButton.setOnClickListener(this)
        binding.blueCircleImageView.setOnClickListener(this)
        binding.greenCircleImageView.setOnClickListener(this)
        binding.redCircleImageView.setOnClickListener(this)
        binding.blueTriangleImageView.setOnClickListener(this)
        binding.greenTriangleImageView.setOnClickListener(this)
        binding.redTriangleImageView.setOnClickListener(this)
        binding.blueSquareImageView.setOnClickListener(this)
        binding.greenSquareImageView.setOnClickListener(this)
        binding.redSquareImageView.setOnClickListener(this)

        filterImages()
        buttonShow()
        buttonsBlock()
        object : CountDownTimer(millisinFuture, 1000) {
            override fun onTick(remaining: Long) {
                binding.timeTextView.text = (remaining / 1000).toString()

            }

            override fun onFinish() {
                hide()
                buttonOpen()
            }
        }.start()
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.backImageButton -> {
                timer.cancel()
                timerWinGif.cancel()
                requireActivity().supportFragmentManager.apply {
                    beginTransaction().remove(this@VisionFragment)
                        .commit()
                    popBackStack()
                }
            }
            binding.blueCircleImageView -> {
                correctChoice(0)
            }
            binding.greenCircleImageView -> {
                correctChoice(1)
            }
            binding.redCircleImageView -> {
                correctChoice(2)
            }
            binding.blueTriangleImageView -> {
                correctChoice(3)
            }
            binding.greenTriangleImageView -> {
                correctChoice(4)
            }
            binding.redTriangleImageView -> {
                correctChoice(5)
            }
            binding.blueSquareImageView -> {
                correctChoice(6)
            }
            binding.greenSquareImageView -> {
                correctChoice(7)
            }
            binding.redSquareImageView -> {
                correctChoice(8)
            }
        }
    }

    private fun filterImages() {
        for (i in images.indices) {
            if (i < visionGame.selectedImages.size) {
                images[i].apply {
                    setImageResource(visionGame.selectedImages[i])
                    visibility = View.VISIBLE
                }
                ObjectAnimator.ofFloat(images[i], View.ROTATION_Y, 90f, 180f).apply {
                    duration = 500
                    start()
                }
            } else if (i < 5) {
                images[i].visibility = View.GONE
            } else {
                images[i].visibility = View.INVISIBLE
            }
        }
    }


    private fun correctChoice(option: Int) {
        if (visionGame.selectedImagesNumbers[visionGame.queue] == option) {
            rotationOpenY(images[visionGame.queue])
            visionGame.queue++
            win()
        } else {
            lose()
        }
    }

    private fun win() {
        if (visionGame.queue == visionGame.selectedImages.size) {
            uncompressedButtons()
            buttonsBlock()
            visionGame.score++
            when (visionGame.score) {
                3 -> {
                    millisinFuture += 1000L
                }
                6 -> {
                    millisinFuture += 1000L
                }
            }
            binding.scoreTextView.text = visionGame.score.toString()

            timer.cancel()
            timerWinGif.start()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun lose() {
        timer.cancel()
        val loseFragment = LoseFragment()

        GlobalScope.launch {
            if (visionGame.score > MyApplication.database.bestScoresDao()
                    .getBestScores()[0].visionBestScores
            ) {
                val bestScores = MyApplication.database.bestScoresDao().getBestScores()[0]
                bestScores.visionBestScores = visionGame.score
                MyApplication.database.bestScoresDao().update(bestScores)
            }
        }

        val bundle = Bundle()
        bundle.putInt("score", visionGame.score)
        loseFragment.arguments = bundle
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrameLayout, loseFragment)
            .addToBackStack("Vision")
            .commit()
    }

    private fun hide() {
        for (i in images) {
            rotationHideY(i)
        }
    }

    private fun timer(): CountDownTimer {
        return object : CountDownTimer(millisinFuture, 1000) {
            override fun onTick(remaining: Long) {
                binding.timeTextView.text = (remaining / 1000).toString()

            }

            override fun onFinish() {
                hide()
                buttonShow()
                buttonOpen()
                binding.chooseConstraintLayout.visibility = View.VISIBLE
            }
        }
    }

    private fun buttonsBlock() {
        binding.blueCircleImageView.isClickable = false
        binding.greenCircleImageView.isClickable = false
        binding.redCircleImageView.isClickable = false
        binding.blueTriangleImageView.isClickable = false
        binding.greenTriangleImageView.isClickable = false
        binding.redTriangleImageView.isClickable = false
        binding.blueSquareImageView.isClickable = false
        binding.greenSquareImageView.isClickable = false
        binding.redSquareImageView.isClickable = false
    }

    private fun buttonShow() {
        rotationX(binding.blueCircleImageView)
        rotationX(binding.greenCircleImageView)
        rotationX(binding.redCircleImageView)
        rotationX(binding.blueTriangleImageView)
        rotationX(binding.greenTriangleImageView)
        rotationX(binding.redTriangleImageView)
        rotationX(binding.blueSquareImageView)
        rotationX(binding.greenSquareImageView)
        rotationX(binding.redSquareImageView)
    }

    private fun buttonOpen() {
        binding.blueCircleImageView.isClickable = true
        binding.greenCircleImageView.isClickable = true
        binding.redCircleImageView.isClickable = true
        binding.blueTriangleImageView.isClickable = true
        binding.greenTriangleImageView.isClickable = true
        binding.redTriangleImageView.isClickable = true
        binding.blueSquareImageView.isClickable = true
        binding.greenSquareImageView.isClickable = true
        binding.redSquareImageView.isClickable = true
    }

    private fun uncompressedButtons() {
        binding.blueCircleImageView.isPressed = false
        binding.greenCircleImageView.isPressed = false
        binding.redCircleImageView.isPressed = false
        binding.blueTriangleImageView.isPressed = false
        binding.greenTriangleImageView.isPressed = false
        binding.redTriangleImageView.isPressed = false
        binding.blueSquareImageView.isPressed = false
        binding.greenSquareImageView.isPressed = false
        binding.redSquareImageView.isPressed = false
    }

    private fun rotationHideY(view: View) {
        ObjectAnimator.ofFloat(view, View.ROTATION_Y, 180f, 270f).apply {
            duration = 500
            start()
        }
    }

    private fun rotationOpenY(view: View) {
        ObjectAnimator.ofFloat(view, View.ROTATION_Y, 270f, 360f).apply {
            duration = 500
            start()
        }
    }

    private fun rotationX(view: View) {
        ObjectAnimator.ofFloat(view, View.ROTATION_X, 270f, 360f).apply {
            duration = 500
            start()
        }
    }
}