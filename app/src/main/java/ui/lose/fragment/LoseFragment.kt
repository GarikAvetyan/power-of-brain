package ui.lose.fragment

import application.MyApplication
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fbf.common.base.BaseCommonFragment
import com.fbf.powerofbrain.R
import com.fbf.powerofbrain.databinding.FragmentLoseBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ui.home.fragment.HomeFragment

class LoseFragment : BaseCommonFragment(), View.OnClickListener {
    private lateinit var binding: FragmentLoseBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoseBinding.inflate(layoutInflater)
        val view = binding.root

        binding.scoreNumberTextView.text = arguments?.get("score").toString()
        backButtonBlock()

        return view
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch {
            binding.bestScoreNumberTextView.text =
                MyApplication.database.bestScoresDao().getBestScores()[0].mathBestScores.toString()
        }

        binding.restartImageView.setOnClickListener(this)
        binding.homeImageView.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.restartImageView -> {
                requireActivity().supportFragmentManager.popBackStack()
            }
            binding.homeImageView -> {
                requireActivity().supportFragmentManager.fragments.clear()
                val homeFragment = HomeFragment()
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.mainFrameLayout, homeFragment)
                    .commit()
            }
        }
    }
}