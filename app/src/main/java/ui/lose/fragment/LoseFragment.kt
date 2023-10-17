package ui.lose.fragment

import android.media.MediaPlayer
import application.MyApplication
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fbf.common.base.BaseCommonFragment
import com.fbf.powerofbrain.R
import com.fbf.powerofbrain.databinding.FragmentLoseBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ui.home.fragment.HomeFragment
import util.Constants
import util.Preferance

class LoseFragment : BaseCommonFragment() {
    private lateinit var binding: FragmentLoseBinding
    private var mInterstitialAd: InterstitialAd? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoseBinding.inflate(layoutInflater)
        val view = binding.root

        soundClick =
        MediaPlayer.create(requireContext(), R.raw.sound_click)
        soundEnable = Preferance.getBooleanPreferance(requireActivity(), Constants.SOUND_ENABLE)

        binding.scoreNumberTextView.text = arguments?.get("score").toString()

        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(requireActivity(),"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
                mInterstitialAd?.show(requireActivity())
            }
        })

        return view
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch {
            val count = requireActivity().supportFragmentManager.backStackEntryCount
            val backStackEntry =
                requireActivity().supportFragmentManager.getBackStackEntryAt(count - 1)

            when (backStackEntry.name) {
                Constants.MATH -> {
                    binding.bestScoreNumberTextView.text =
                        MyApplication.database.bestScoresDao()
                            .getBestScores()[0].mathBestScores.toString()
                }
                Constants.ACTION -> {
                    binding.bestScoreNumberTextView.text =
                        MyApplication.database.bestScoresDao()
                            .getBestScores()[0].actionBestScores.toString()
                }
                Constants.MEMORY -> {
                    binding.bestScoreNumberTextView.text =
                        MyApplication.database.bestScoresDao()
                            .getBestScores()[0].memoryBestScores.toString()
                }
                Constants.VISION -> {
                    binding.bestScoreNumberTextView.text =
                        MyApplication.database.bestScoresDao()
                            .getBestScores()[0].visionBestScores.toString()
                }
                Constants.PATIENCE -> {
                    binding.bestScoreNumberTextView.text =
                        MyApplication.database.bestScoresDao()
                            .getBestScores()[0].patienceBestScores.toString()
                }
                Constants.SPEED -> {
                    binding.bestScoreNumberTextView.text =
                        MyApplication.database.bestScoresDao()
                            .getBestScores()[0].speedBestScores.toString()
                }
            }
        }

        binding.restartImageView.setOnClickListener(this)
        binding.homeImageView.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (soundEnable) {
            soundClick.start()
        }
        when (view) {
            binding.restartImageView -> {
                soundClick.start()
                requireActivity().supportFragmentManager.popBackStack()
            }
            binding.homeImageView -> {
                soundClick.start()
                requireActivity().supportFragmentManager.fragments.clear()
                val homeFragment = HomeFragment()
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.mainFrameLayout, homeFragment)
                    .remove(this)
                    .commit()
            }
        }
    }
}