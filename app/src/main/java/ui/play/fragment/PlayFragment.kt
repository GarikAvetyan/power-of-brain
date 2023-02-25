package ui.play.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fbf.common.base.BaseCommonFragment
import com.fbf.powerofbrain.R
import com.fbf.powerofbrain.databinding.FragmentPlayBinding
import ui.action.fragment.ActionFragment
import ui.math.fragment.MathFragment
import ui.memory.fragment.MemoryFragment
import ui.patience.fragment.PatienceFragment
import ui.vision.fragment.VisionFragment

class PlayFragment : BaseCommonFragment() {
    private lateinit var binding: FragmentPlayBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayBinding.inflate(layoutInflater)
        val view = binding.root

        backButtonBlock()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backImageButton.setOnClickListener(this)
        binding.mathConstraintLayout.setOnClickListener(this)
        binding.actionConstraintLayout.setOnClickListener(this)
        binding.memoryConstraintLayout.setOnClickListener(this)
        binding.visionConstraintLayout.setOnClickListener(this)
        binding.patienceConstraintLayout.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.backImageButton -> {
                requireActivity().supportFragmentManager.apply {
                    beginTransaction().remove(this@PlayFragment)
                        .commit()
                    popBackStack()
                }
            }
            binding.mathConstraintLayout -> {
                val mathFragment = MathFragment()
                replaceFragment(mathFragment)
            }
            binding.actionConstraintLayout -> {
                val actionFragment = ActionFragment()
                replaceFragment(actionFragment)
            }
            binding.memoryConstraintLayout -> {
                val memoryFragment = MemoryFragment()
                replaceFragment(memoryFragment)
            }
            binding.visionConstraintLayout -> {
                val visionFragment = VisionFragment()
                replaceFragment(visionFragment)
            }
            binding.patienceConstraintLayout -> {
                val patienceFragment = PatienceFragment()
                replaceFragment(patienceFragment)
            }
        }
    }

    private fun replaceFragment(fragment: BaseCommonFragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrameLayout, fragment)
            .addToBackStack("")
            .commit()
    }
}