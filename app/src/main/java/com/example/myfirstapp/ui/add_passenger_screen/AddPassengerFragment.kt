package com.example.myfirstapp.ui.add_passenger_screen

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.myfirstapp.R
import com.example.myfirstapp.databinding.FragmentAddPassengerBinding
import com.example.myfirstapp.domain.entity.Passenger
import com.example.myfirstapp.ui.DROP_DOWN_STATION_PREF
import com.example.myfirstapp.ui.PREFERENCE
import com.example.myfirstapp.ui.add_itinerary_screen.KEY_PARENT_ID
import com.example.myfirstapp.utils.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import java.util.Calendar.*

class AddPassengerFragment : Fragment(R.layout.fragment_add_passenger) {
    companion object {
        fun newInstance(bundle: Bundle): AddPassengerFragment {
            val fragment = AddPassengerFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var parentId: String

    private val binding: FragmentAddPassengerBinding by viewBinding()
    private val viewModel: AddPassengerViewModel by viewModel()
    private lateinit var preference: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var arrayAdapter: ArrayAdapter<String>

    private var dateAndTimeDeparture: Calendar? = null
    private var dateAndTimeArrival: Calendar? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        preference = requireActivity().getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
        editor = preference.edit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { bundle ->
            parentId = bundle.getString(KEY_PARENT_ID).toString()
        }

        binding.stationDepartureEditText.setOnClickListener {
            binding.stationDepartureEditText.setAdapter(
                initDropDownAdapter(
                    getListStationFromPreference()
                )
            )
        }

        binding.stationArrivalEditText.setOnClickListener {
            binding.stationArrivalEditText.setAdapter(
                initDropDownAdapter(
                    getListStationFromPreference()
                )
            )
        }

        binding.dateDepartureLayout.setOnClickListener {
            val timePicker = getTimePicker("Время прибытия", getInstance())
            timePicker.addOnPositiveButtonClickListener {
                dateAndTimeDeparture?.let {
                    it.set(HOUR, timePicker.hour)
                    it.set(MINUTE, timePicker.minute)
                }
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
            val timePicker = getTimePicker("Время отправления", getInstance())
            timePicker.addOnPositiveButtonClickListener {
                dateAndTimeArrival?.let {
                    it.set(HOUR, timePicker.hour)
                    it.set(MINUTE, timePicker.minute)
                }
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
    }

    override fun onDestroyView() {
        savePassenger()
        super.onDestroyView()
    }

    private fun savePassenger() {
        viewModel.savePassenger(passenger = getCurrentPassenger())
    }

    private fun getCurrentPassenger(): Passenger {
        return Passenger(
            id = generateStringID(),
            itineraryID = parentId,
            departureTime = dateAndTimeDeparture,
            arrivalTime = dateAndTimeArrival,
            departureStation = getDepartureStation(),
            arrivalStation = getArrivalStation(),
            numberOfTrain = getNumberOfTrain(),
            notes = getNotes()
        )
    }

    private fun getNotes(): String? {
        val value = binding.notesEditText.text.toString()
        return value.ifBlank { null }
    }

    private fun getNumberOfTrain(): String? {
        val value = binding.numberTrainEditText.text.toString()
        return value.ifBlank { null }
    }

    private fun getArrivalStation(): String? {
        val value = binding.stationArrivalEditText.text.toString()
        return value.ifBlank { null }
    }

    private fun getDepartureStation(): String? {
        val value = binding.stationDepartureEditText.text.toString()
        return value.ifBlank { null }
    }

    private fun getListStationFromPreference(): List<String> {
        var setStation: MutableSet<String> = mutableSetOf()
        if (preference.contains(DROP_DOWN_STATION_PREF)) {
            setStation = preference.getStringSet(DROP_DOWN_STATION_PREF, null)!!
        }
        return mutableListOf<String>().apply { addAll(setStation) }
    }

    private fun saveStationInPreference(stationName: String?) {
        if (!stationName.isNullOrBlank()) {
            val setStation = getListStationFromPreference()
            val copySetStation: MutableSet<String?> = mutableSetOf<String?>().apply {
                addAll(setStation)
                add(stationName)
            }

            editor.putStringSet(DROP_DOWN_STATION_PREF, copySetStation).apply {
                apply()
            }
        }
    }

    private fun initDropDownAdapter(stations: List<String>): ArrayAdapter<String> {
        arrayAdapter = ArrayAdapter(
            requireContext(),
            R.layout.station_drop_down_menu,
            stations
        )
        return arrayAdapter
    }
}