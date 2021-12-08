package com.example.myfirstapp.ui.addFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myfirstapp.R
import com.example.myfirstapp.databinding.FragmentAddItineraryBinding

class AddItineraryFragment : Fragment() {

    private var _binding: FragmentAddItineraryBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddItineraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddLoco.setOnClickListener {
            activity?.supportFragmentManager?.apply {
                beginTransaction()
                    .replace(R.id.container, AddLocoFragment())
                    .addToBackStack("2")
                    .commit()
            }
        }
    }


}