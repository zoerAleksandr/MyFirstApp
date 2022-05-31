package com.example.myfirstapp.ui.add_passenger_screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.myfirstapp.R
import com.example.myfirstapp.databinding.FragmentAddPassengerBinding
import com.example.myfirstapp.utils.getDatePicker
import com.example.myfirstapp.utils.getTimePicker
import com.example.myfirstapp.utils.setTextDate
import com.example.myfirstapp.utils.setTextTime
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import java.util.Calendar.*

const val KEY_PARENT_ID = "keyParentId"
const val KEY_PASSENGER_ID = "keyPassengerId"

class AddPassengerFragment : Fragment(R.layout.fragment_add_passenger) {
    companion object {
        fun newInstance(bundle: Bundle): AddPassengerFragment {
            val fragment = AddPassengerFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var passengerId: String
    private lateinit var parentId: String

    private val binding: FragmentAddPassengerBinding by viewBinding()
    private val viewModel: AddPassengerViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { bundle ->
            passengerId = bundle.getString(KEY_PASSENGER_ID).toString()
            parentId = bundle.getString(KEY_PARENT_ID).toString()
        }
        binding.numberTrainEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                viewModel.saveNumberTrain(passengerId, binding.numberTrainEditText.text.toString())
            }
        }

        binding.stationDepartureEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                viewModel.saveStationDeparture(
                    passengerId,
                    binding.stationDepartureEditText.text.toString()
                )
            }
        }

        binding.stationArrivalEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                viewModel.saveStationArrival(
                    passengerId,
                    binding.stationArrivalEditText.text.toString()
                )
            }
        }

        binding.dateDepartureLayout.setOnClickListener {
            var dateAndTimeDeparture: Calendar? = null

            val timePicker = getTimePicker("Время прибытия", getInstance())
            timePicker.addOnPositiveButtonClickListener {
                dateAndTimeDeparture?.let {
                    it.set(HOUR, timePicker.hour)
                    it.set(MINUTE, timePicker.minute)
                }
                viewModel.saveDateDeparture(passengerId, dateAndTimeDeparture)
                binding.apply {
                    timeDepartureTextView.apply {
                        text = setTextTime(timePicker)
                        alpha = 1f
                    }
                }
            }

            val datePicker = getDatePicker("Дата прибытия", null)
            datePicker.show(requireActivity().supportFragmentManager, null)
            datePicker.addOnPositiveButtonClickListener {
                dateAndTimeDeparture = getInstance().apply {
                    timeInMillis = it
                }
                binding.dateDepartureTextView.apply {
                    text = setTextDate(it)
                    alpha = 1f
                }
                timePicker.show(requireActivity().supportFragmentManager, null)
            }
        }

        binding.dateArrivalLayout.setOnClickListener {
            var dateAndTimeArrival: Calendar? = null

            val timePicker = getTimePicker("Время отправления", getInstance())
            timePicker.addOnPositiveButtonClickListener {
                dateAndTimeArrival?.let {
                    it.set(HOUR, timePicker.hour)
                    it.set(MINUTE, timePicker.minute)
                }
                viewModel.saveDateArrival(passengerId, dateAndTimeArrival)
                binding.timeArrivalTextView.apply {
                    text = setTextTime(timePicker)
                    alpha = 1f
                }
            }

            val datePicker = getDatePicker("Дата отправления", null)
            datePicker.show(requireActivity().supportFragmentManager, null)
            datePicker.addOnPositiveButtonClickListener {
                dateAndTimeArrival = getInstance().apply {
                    timeInMillis = it
                }
                binding.dateArrivalTextView.apply {
                    text = setTextDate(it)
                    alpha = 1f
                }
                timePicker.show(requireActivity().supportFragmentManager, null)
            }
        }

        binding.notesEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                viewModel.saveNotes(
                    passengerId,
                    binding.notesEditText.text.toString()
                )
            }
        }
    }
}