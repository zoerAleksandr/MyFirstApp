package com.example.myfirstapp.ui.add_train_screen

import androidx.recyclerview.widget.RecyclerView
import com.example.myfirstapp.databinding.ItemStationBinding
import com.example.myfirstapp.domain.entity.Station
import java.util.*

class AddTrainViewHolder(private val binding: ItemStationBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val dateAndTimeNow = Calendar.getInstance()
    private val titleArrivalTimePicker = "Время прибытия"
    private val titleDepartureTimePicker = "Время отправления"

    fun bind(station: Station) {}
}