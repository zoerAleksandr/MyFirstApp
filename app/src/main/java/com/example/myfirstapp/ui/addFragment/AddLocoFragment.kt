package com.example.myfirstapp.ui.addFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myfirstapp.databinding.FragmentAddLocoBinding
import com.google.android.material.tabs.TabLayout

class AddLocoFragment : Fragment() {

    private var _binding: FragmentAddLocoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddLocoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tabLayoutLoco.apply {
            addTab(this.newTab().setText("Тепловоз"), 0, false)
            addTab(this.newTab().setText("Электровоз"), 1, true)
        }

        binding.tabLayoutLocoSection.apply {
            addTab(this.newTab().setText("1"), 0, false)
            addTab(this.newTab().setText("2"), 1, true)
            addTab(this.newTab().setText("3"), 2, false)

            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    when (tab.position) {
                        0 -> {
                            binding.blockEnergySection2.visibility = View.GONE
                            binding.blockEnergySection3.visibility = View.GONE
                        }
                        1 -> {
                            binding.blockEnergySection2.visibility = View.VISIBLE
                            binding.blockEnergySection3.visibility = View.GONE
                        }
                        2 -> binding.blockEnergySection3.visibility = View.VISIBLE

                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {

                }

                override fun onTabReselected(tab: TabLayout.Tab) {

                }
            })
        }
    }
}