package com.example.myfirstapp.ui.add_train_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirstapp.databinding.ItemStationBinding
import com.example.myfirstapp.domain.entity.Station

const val LIST_STATION = "LIST_STATION"

class AddTrainFragmentAdapter : RecyclerView.Adapter<AddTrainViewHolder>() {

    private var listStation: MutableList<Station> = mutableListOf()

    fun setData(list: MutableList<Station>) {
        listStation.addAll(list)
    }

    fun addStation(station: Station) {
        listStation.add(station)
        notifyItemInserted(listStation.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddTrainViewHolder {
        val binding = ItemStationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AddTrainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddTrainViewHolder, position: Int) {
        holder.bind(listStation[position])
    }

    override fun getItemCount() = listStation.size
}