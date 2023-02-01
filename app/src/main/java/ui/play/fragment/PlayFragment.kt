package ui.play.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fbf.common.base.BaseCommonFragment
import com.fbf.powerofbrain.R
import com.fbf.powerofbrain.databinding.FragmentHomeBinding
import com.fbf.powerofbrain.databinding.FragmentPlayBinding
import ui.math.fragment.MathFragment

class PlayFragment : BaseCommonFragment(), View.OnClickListener {
    private lateinit var binding: FragmentPlayBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayBinding.inflate(layoutInflater)
        val view = binding.root

        backButtonBlock()

        binding.backImageButton.setOnClickListener(this)
        binding.mathConstraintLayout.setOnClickListener(this)

        return view
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.backImageButton -> {
                requireActivity().supportFragmentManager.popBackStack()
                requireActivity().supportFragmentManager.fragments.clear()
            }
            binding.mathConstraintLayout -> {
                val mathFragment = MathFragment()
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.mainFrameLayout, mathFragment)
                    .addToBackStack("")
                    .commit()
            }
        }
    }
}