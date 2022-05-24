package com.example.myfirstapp.ui.add_train_screen

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.myfirstapp.R
import com.example.myfirstapp.databinding.FragmentAddTrainBinding
import com.example.myfirstapp.ui.add_loco_screen.PREFERENCES
import com.example.myfirstapp.utils.AppStateAddTrain
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
        val sharedPreferences = requireActivity().getSharedPreferences(
            PREFERENCES,
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences?.edit()

// экземпляр настроек полученых из сохраненного состояния
        val set: MutableSet<String>? =
            sharedPreferences?.getStringSet(LIST_STATION, mutableSetOf())
// копия настроек для редактирования
        val setCopy: MutableSet<String> = mutableSetOf<String>().apply {
            addAll(set!!)
        }
// список для адаптера AutoCompileTextView
        val string: MutableList<String> = mutableListOf<String>().apply {
            addAll(setCopy)
        }

        val customAdapterDropDown =
            CustomAdapterDropDown(requireContext(), R.layout.item_drop_down_btn_remove, string)

        viewModel.getData(123L).observe(viewLifecycleOwner) { state -> renderData(state) }

        adapter = AddTrainFragmentAdapter(requireActivity(), customAdapterDropDown)
        binding.recyclerTrain.adapter = adapter

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
//            viewModel.insert(Station(null,null, null, null))
//            adapter.addStation(Station(null, null, null))
        }
    }

    private fun renderData(state: AppStateAddTrain) {
        when (state) {
            is AppStateAddTrain.Loading -> {

            }
            is AppStateAddTrain.Success -> {
                // передал в адаптер новый массив станций
                if (state.stationRoomEntity.isNotEmpty()) {
                    binding.textEmptyStationList.visibility = View.GONE
                    adapter.setData(state.stationRoomEntity)
                } else {
                    binding.textEmptyStationList.visibility = View.VISIBLE
                }
            }
            is AppStateAddTrain.Error -> {

            }
        }
    }
}