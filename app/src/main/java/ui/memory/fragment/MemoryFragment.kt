package ui.memory.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fbf.common.base.BaseCommonFragment
import com.fbf.powerofbrain.R
import com.fbf.powerofbrain.databinding.FragmentActionBinding
import com.fbf.powerofbrain.databinding.FragmentMemoryBinding

class MemoryFragment : BaseCommonFragment() {
    private lateinit var binding: FragmentMemoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMemoryBinding.inflate(layoutInflater)
        val view = binding.root

        backButtonBlock()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}