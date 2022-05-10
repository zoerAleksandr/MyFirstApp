package com.example.myfirstapp.ui.add_train_screen

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.myfirstapp.R
import com.example.myfirstapp.databinding.FragmentAddTrainBinding
import com.example.myfirstapp.ui.add_loco_screen.PREFERENCES
import com.example.myfirstapp.utils.AppStateAddTrain

class AddTrainFragment : Fragment(R.layout.fragment_add_train) {
    private val binding: FragmentAddTrainBinding by viewBinding()
    private lateinit var adapter: AddTrainFragmentAdapter

    private val viewModel: ViewModelAddTrainFragment by lazy {
        ViewModelProvider(this)[ViewModelAddTrainFragment::class.java]
    }

    @SuppressLint("CommitPrefEdits")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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