package com.example.myfirstapp.ui.add_passenger_screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.myfirstapp.R
import com.example.myfirstapp.databinding.FragmentAddPassengerBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

const val KEY_PARENT_ID = "keyParentId"
const val KEY_PASSENGER_ID = "keyPassengerId"

class AddPassengerFragment : Fragment(R.layout.fragment_add_passenger) {
    companion object{
        fun newInstance(bundle: Bundle): AddPassengerFragment{
            val fragment = AddPassengerFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val binding : FragmentAddPassengerBinding by viewBinding()
    private val viewModel: AddPassengerViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.numberTrainEditText.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus){
                viewModel.saveNumberTrain(binding.numberTrainEditText.text.toString())
            }
        }
    }
}