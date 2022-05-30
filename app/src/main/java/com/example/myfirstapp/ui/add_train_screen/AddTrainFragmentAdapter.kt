package com.example.myfirstapp.ui.add_train_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirstapp.databinding.ItemStationBinding
import com.example.myfirstapp.domain.entity.Station

class AddTrainFragmentAdapter(
    private val timeArrivalClickListener: (Station) -> Unit,
    private val timeDepartureClickListener: (Station) -> Unit,
    private val textStationChangedListener: (String?, String) -> Unit,
//    private val dropDownAdapter: ArrayAdapter<String>
) : RecyclerView.Adapter<AddTrainViewHolder>() {
    private lateinit var dropDownAdapter: ArrayAdapter<String>
    private var listStation: MutableList<Station> = mutableListOf()

    fun addStation(station: Station) {
        listStation.add(station)
        notifyItemInserted(listStation.size - 1)
    }

    fun updateStation(station: Station) {
        val index = listStation.indexOf(
            listStation.find { it.stationID == station.stationID }
        )
        listStation[index] = station
        notifyItemChanged(index)
    }

    fun initDropDownAdapter(adapter: ArrayAdapter<String>){
        dropDownAdapter = adapter
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
        val station = listStation[position]
        holder.bind(
            station,
            timeArrivalClickListener,
            timeDepartureClickListener,
            textStationChangedListener,
            dropDownAdapter
        )
    }

    override fun getItemCount() = listStation.size
}