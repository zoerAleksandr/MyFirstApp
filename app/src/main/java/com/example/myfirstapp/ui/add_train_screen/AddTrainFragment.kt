package com.example.myfirstapp.ui.add_train_screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.myfirstapp.R
import com.example.myfirstapp.databinding.FragmentAddTrainBinding
import com.example.myfirstapp.domain.entity.Station
import com.example.myfirstapp.utils.AddTrainState
import com.example.myfirstapp.utils.generateStringID
import org.koin.androidx.viewmodel.ext.android.viewModel

const val KEY_TRAIN_DATA_ID = "keyTrainDataId"
const val KEY_TRAIN_DATA_PARENT_ID = "keyTParentId"

class AddTrainFragment : Fragment(R.layout.fragment_add_train) {
    companion object {
        fun newInstance(bundle: Bundle): AddTrainFragment {
            val fragment = AddTrainFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var trainDataId: String

    private val binding: FragmentAddTrainBinding by viewBinding()
    private lateinit var adapter: AddTrainFragmentAdapter

    private val viewModel: AddTrainViewModel by viewModel()

    @SuppressLint("CommitPrefEdits")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            trainDataId = it.getString(KEY_TRAIN_DATA_ID).toString()
        }

        viewModel.getData().observe(viewLifecycleOwner) { state ->
            renderData(state)
        }

        initAdapter()

        binding.dataNumberTrain.addTextChangedListener {
            viewModel.saveNumberOfTrain(trainDataId, it.toString().toIntOrNull())
        }

        binding.dataWeightTrain.addTextChangedListener {
            viewModel.saveWeight(trainDataId, it.toString().toIntOrNull())
        }

        binding.dataAxleTrain.addTextChangedListener {
            viewModel.saveWheelAxle(trainDataId, it.toString().toIntOrNull())
        }

        binding.dataConditionalLengthTrain.addTextChangedListener {
            viewModel.saveConditionalLength(trainDataId, it.toString().toIntOrNull())
        }

        binding.btnAddStation.setOnClickListener {
            val station = Station(
                stationID = generateStringID(),
                trainDataID = trainDataId,
                stationName = null,
                arrivalTime = null,
                departureTime = null
            )
            viewModel.saveStation(trainDataId, station)
        }
    }

    private fun initAdapter() {
        adapter = AddTrainFragmentAdapter()
        binding.recyclerTrain.adapter = adapter
    }

    private fun renderData(state: AddTrainState) {
        when (state) {
            is AddTrainState.Loading -> {

            }
            is AddTrainState.Success -> {
                // передал в адаптер новый массив станций
                binding.textEmptyStationList.visibility = View.GONE
                adapter.addStation(state.station)
            }
            is AddTrainState.Error -> {

            }
        }
    }
}