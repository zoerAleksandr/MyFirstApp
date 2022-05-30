package com.example.myfirstapp.ui.add_train_screen

import android.text.SpannableStringBuilder
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirstapp.R
import com.example.myfirstapp.databinding.ItemStationBinding
import com.example.myfirstapp.domain.entity.Station
import java.util.Calendar.HOUR_OF_DAY
import java.util.Calendar.MINUTE

class AddTrainViewHolder(
    private val binding: ItemStationBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        station: Station,
        timeArrivalClickListener: (Station) -> Unit,
        timeDepartureClickListener: (Station) -> Unit,
        textStationChangedListener: (String?, String) -> Unit,
        dropDownAdapter: ArrayAdapter<String>
    ) {
        binding.timeArrivalLayout.setOnClickListener {
            timeArrivalClickListener.invoke(station)
        }
        binding.timeDepartureLayout.setOnClickListener {
            timeDepartureClickListener.invoke(station)
        }
        binding.dataStation.setAdapter(dropDownAdapter)

        binding.dataStation.text =
            if (station.stationName.isNullOrBlank()) {
                SpannableStringBuilder("")
            } else {
                SpannableStringBuilder(station.stationName.toString())
            }

        /** Здесь происходит сохранение stationName в Room*/
        binding.dataStation.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                textStationChangedListener(binding.dataStation.text.toString(), station.stationID)
            }
        }

        station.arrivalTime?.let { calendar ->
            val hour = if (calendar.get(HOUR_OF_DAY).toString().length == 1) {
                "0${calendar.get(HOUR_OF_DAY)}"
            } else {
                calendar.get(HOUR_OF_DAY).toString()
            }
            val minute = if (calendar.get(MINUTE).toString().length == 1) {
                "0${calendar.get(MINUTE)}"
            } else {
                calendar.get(MINUTE).toString()
            }

            binding.timeArrivalTextView.apply {
                text = resources.getString(
                    R.string.text_time, hour, minute
                )
                alpha = 1f
            }
        }

        station.departureTime?.let { calendar ->
            val hour = if (calendar.get(HOUR_OF_DAY).toString().length == 1) {
                "0${calendar.get(HOUR_OF_DAY)}"
            } else {
                calendar.get(HOUR_OF_DAY).toString()
            }
            val minute = if (calendar.get(MINUTE).toString().length == 1) {
                "0${calendar.get(MINUTE)}"
            } else {
                calendar.get(MINUTE).toString()
            }

            binding.timeDepartureTextView.apply {
                text = resources.getString(
                    R.string.text_time, hour, minute
                )
                alpha = 1f
            }
        }
    }
}