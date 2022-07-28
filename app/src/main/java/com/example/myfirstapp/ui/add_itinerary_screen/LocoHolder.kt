package com.example.myfirstapp.ui.add_itinerary_screen

import androidx.recyclerview.widget.RecyclerView
import com.example.myfirstapp.App
import com.example.myfirstapp.R
import com.example.myfirstapp.databinding.ItemItineraryLocoBinding
import com.example.myfirstapp.domain.entity.LocomotiveData
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.Calendar.HOUR_OF_DAY
import java.util.Calendar.MINUTE

class LocoHolder(private val binding: ItemItineraryLocoBinding) :
    RecyclerView.ViewHolder(binding.root), KoinComponent {

    private val context: App by inject()

    fun bind(locomotive: LocomotiveData) {
        binding.seriesAndNumberLoco.text =
            context.resources.getString(
                R.string.text_locomotive_data,
                locomotive.series.toString(),
                locomotive.number.toString()
            )
        binding.timeLocoStart.text =
            context.resources.getString(
                R.string.text_time,
                locomotive.startAcceptance?.get(HOUR_OF_DAY),
                locomotive.startAcceptance?.get(MINUTE)
            )
        binding.timeLocoEnd.text =
            context.resources.getString(
                R.string.text_time,
                locomotive.endDelivery?.get(HOUR_OF_DAY),
                locomotive.endDelivery?.get(MINUTE)
            )
    }
}