package com.example.myfirstapp.ui.add_train_screen

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.myfirstapp.R
import com.example.myfirstapp.databinding.FragmentAddTrainBinding
import com.example.myfirstapp.domain.entity.Station
import com.example.myfirstapp.domain.entity.TrainData
import com.example.myfirstapp.ui.DROP_DOWN_STATION_PREF
import com.example.myfirstapp.ui.PREFERENCE
import com.example.myfirstapp.ui.add_itinerary_screen.KEY_PARENT_ID
import com.example.myfirstapp.utils.AddTrainState
import com.example.myfirstapp.utils.generateStringID
import com.example.myfirstapp.utils.getTimePicker
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import java.util.Calendar.HOUR_OF_DAY
import java.util.Calendar.MINUTE

class AddTrainFragment : Fragment(R.layout.fragment_add_train) {
    companion object {
        fun newInstance(bundle: Bundle): AddTrainFragment {
            val fragment = AddTrainFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var itineraryId: String
    private val trainDataId = generateStringID()

    private val binding: FragmentAddTrainBinding by viewBinding()
    private lateinit var adapter: AddTrainFragmentAdapter

    private val viewModel: AddTrainViewModel by viewModel()
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var preference: SharedPreferences

    override fun onAttach(context: Context) {
        super.onAttach(context)
        preference = requireActivity().getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
        editor = preference.edit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            itineraryId = it.getString(KEY_PARENT_ID).toString()
            Log.d("Debug", itineraryId)
        }

        initAdapter()

        viewModel.newStationObserve().observe(viewLifecycleOwner) { state ->
            renderData(state)
        }

        viewModel.changeStationObserve().observe(viewLifecycleOwner) { state ->
            updateStation(state)
        }

        saveTrainData()

        binding.btnAddStation.setOnClickListener {
            getEmptyStation().also { newStation ->
                viewModel.saveStation(station = newStation)
                adapter.addStation(station = newStation)
            }
        }
    }

    private fun getEmptyStation(): Station {
        return Station(
            id = generateStringID(),
            trainDataID = trainDataId,
            null,
            null,
            null
        )
    }

    private fun getListStation(): List<String> {
        var setStation: MutableSet<String> = mutableSetOf()
        if (preference.contains(DROP_DOWN_STATION_PREF)) {
            setStation = preference.getStringSet(DROP_DOWN_STATION_PREF, null)!!
        }
        return mutableListOf<String>().apply { addAll(setStation) }
    }

    private fun getDropDownAdapter(listStation: List<String>): ArrayAdapter<String> {
        return ArrayAdapter(
            requireContext(),
            R.layout.station_drop_down_menu,
            listStation
        )
    }

    private fun initAdapter() {
        adapter = AddTrainFragmentAdapter(
            { station ->
                timeArrivalClickListener(station)
            },
            { station ->
                timeDepartureClickListener(station)
            },
            { editable, stationId ->
                textStationChangedListener(editable, stationId)
            },
        ).apply {
            initDropDownAdapter(getDropDownAdapter(getListStation()))
        }
        binding.recyclerTrain.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerTrain.recycledViewPool.setMaxRecycledViews(0, 0)
        binding.recyclerTrain.adapter = adapter
    }

    private fun renderData(state: AddTrainState) {
        when (state) {
            is AddTrainState.Loading -> {}
            is AddTrainState.Success -> {
                binding.textEmptyStationList.visibility = View.GONE
                adapter.addStation(state.station)
            }
            is AddTrainState.Error -> {}
        }
    }

    private fun updateStation(state: AddTrainState) {
        when (state) {
            is AddTrainState.Success -> {
                adapter.updateStation(state.station)
            }
            is AddTrainState.Loading -> {}
            is AddTrainState.Error -> {}
        }
    }

    /** Сюда нужно передать Calendar приемки локомотива или явки на работу*/

    private fun timeArrivalClickListener(station: Station) {
        val currentTime = Calendar.getInstance()
        getTimePicker("Время прибытия", Calendar.getInstance()).also { timePicker ->
            timePicker.show(requireActivity().supportFragmentManager, "TIME_PICKER_ARRIVAL")
            timePicker.addOnPositiveButtonClickListener {
                currentTime.set(HOUR_OF_DAY, timePicker.hour)
                currentTime.set(MINUTE, timePicker.minute)
            }
        }
    }

    private fun timeDepartureClickListener(station: Station) {
        val currentTime = Calendar.getInstance()
        getTimePicker("Время отправления", Calendar.getInstance()).also { timePicker ->
            timePicker.show(requireActivity().supportFragmentManager, "TIME_PICKER_DEPARTURE")
            timePicker.addOnPositiveButtonClickListener {
                currentTime.set(HOUR_OF_DAY, timePicker.hour)
                currentTime.set(MINUTE, timePicker.minute)
            }
        }
    }

    private fun textStationChangedListener(stationName: String?, stationId: String) {

        if (!stationName.isNullOrBlank()) {
            val setStation = getListStation()
            val copySetStation: MutableSet<String?> = mutableSetOf<String?>().apply {
                addAll(setStation)
                add(stationName)
            }

            editor.putStringSet(DROP_DOWN_STATION_PREF, copySetStation).apply {
                apply()
            }
            adapter.initDropDownAdapter(getDropDownAdapter(getListStation()))
        }
    }

    override fun onDestroyView() {
        saveTrainData()
        saveStationList()
        super.onDestroyView()
    }

    private fun saveStationList() {
//        for (station in stationList) {
//            viewModel.saveStation(station)
//        }
    }

    private fun saveTrainData() {
        viewModel.saveTrainData(trainData = getCurrentTrainData())
    }

    private fun getCurrentTrainData(): TrainData {
        return TrainData(
            id = trainDataId,
            itineraryID = itineraryId,
            numberOfTrain = getNumberOfTrain(),
            weight = getWeight(),
            wheelAxle = getWheelAxle(),
            conditionalLength = getConditionalLength()
        )
    }

    private fun getNumberOfTrain(): String? {
        val value = binding.dataNumberTrain.text.toString()
        return value.ifBlank { null }
    }

    private fun getWeight(): String? {
        val value = binding.dataWeightTrain.text.toString()
        return value.ifBlank { null }
    }

    private fun getWheelAxle(): String? {
        val value = binding.dataAxleTrain.text.toString()
        return value.ifBlank { null }
    }

    private fun getConditionalLength(): String? {
        val value = binding.dataConditionalLengthTrain.text.toString()
        return value.ifBlank { null }
    }
}