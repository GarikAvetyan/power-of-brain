package ui.patience.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fbf.common.base.BaseCommonFragment
import com.fbf.powerofbrain.R
import com.fbf.powerofbrain.databinding.FragmentMathBinding
import com.fbf.powerofbrain.databinding.FragmentPatienceBinding


class PatienceFragment : BaseCommonFragment() {
    private lateinit var binding: FragmentPatienceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPatienceBinding.inflate(layoutInflater)
        val view = binding.root

        backButtonBlock()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backImageButton.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.backImageButton -> {
                requireActivity().supportFragmentManager.apply {
                    beginTransaction().remove(this@PatienceFragment)
                        .commit()
                    popBackStack()
                }
            }
        }
    }

}