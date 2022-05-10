package com.example.myfirstapp.ui.add_train_screen

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirstapp.databinding.ItemStationBinding
import com.example.myfirstapp.domain.entity.Station
import com.example.myfirstapp.ui.add_loco_screen.PREFERENCES
import com.example.myfirstapp.utils.setTextTime
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

const val LIST_STATION = "LIST_STATION"

class AddTrainFragmentAdapter(
    val fragmentActivity: FragmentActivity,
    val customAdapterDropDown: CustomAdapterDropDown
) :
    RecyclerView.Adapter<AddTrainFragmentAdapter.MainViewHolder>() {

    inner class MainViewHolder(private val binding: ItemStationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val dateAndTimeNow = Calendar.getInstance()
        private val titleArrivalTimePicker = "Время прибытия"
        private val titleDepartureTimePicker = "Время отправления"

        @SuppressLint("CommitPrefEdits")
        fun bind(station: Station) {
            binding.apply {
                timeArrival.setOnClickListener {
                    val timePicker = MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_24H)
                        .setTitleText(titleArrivalTimePicker)
                        .setHour(dateAndTimeNow.get(Calendar.HOUR_OF_DAY))
                        .setMinute(dateAndTimeNow.get(Calendar.MINUTE))
                        .build()
                    timePicker.show(fragmentActivity.supportFragmentManager, "TIME_PICKER_ARRIVAL")
                    timePicker.addOnPositiveButtonClickListener {
                        station.arrivalTime = setTextTime(timePicker)
                        binding.dataTimeArrival.text = setTextTime(timePicker)
                        binding.dataTimeArrival.alpha = 1f
                    }
                }

                timeDeparture.setOnClickListener {
                    val timePicker = MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_24H)
                        .setTitleText(titleDepartureTimePicker)
                        .setHour(dateAndTimeNow.get(Calendar.HOUR_OF_DAY))
                        .setMinute(dateAndTimeNow.get(Calendar.MINUTE))
                        .build()
                    timePicker.show(
                        fragmentActivity.supportFragmentManager,
                        "TIME_PICKER_DEPARTURE"
                    )
                    timePicker.addOnPositiveButtonClickListener {
                        station.departureTime = setTextTime(timePicker)
                        binding.dataTimeDeparture.text = setTextTime(timePicker)
                        binding.dataTimeDeparture.alpha = 1f
                    }
                }
//                dataStation.addTextChangedListener {
// // TODO
//                }
                // SharedPreferences
                val sharedPreferences = fragmentActivity.getSharedPreferences(
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

                binding.dataStation.setAdapter(customAdapterDropDown)

                binding.dataStation.threshold = 1
//                ArrayAdapter(
//                    fragmentActivity,
//                    R.layout.simple_spinner_dropdown_item,
//                    string
//                ).also { arrayAdapter ->
//                    binding.dataStation.setAdapter(arrayAdapter)
//                }
// Сохранение введеных данных при потере фокуса
                binding.dataStation.setOnFocusChangeListener { _, _ ->
                    if (binding.dataStation.text.toString()
                            .isNotBlank() && setCopy.add(binding.dataStation.text.toString())
                    ) {
                        editor?.putStringSet(LIST_STATION, setCopy)?.apply()
                    }
                }
            }
        }
    }

    private var listStationRoomEntity: MutableList<Station> = mutableListOf()

    fun setData(list: MutableList<Station>) {
        listStationRoomEntity.addAll(list)
    }

//    fun addStation(station: Station) {
//        listStation.add(station)
//        notifyItemInserted(listStation.size - 1)
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = ItemStationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(listStationRoomEntity[position])
    }

    override fun getItemCount() = listStationRoomEntity.size
}