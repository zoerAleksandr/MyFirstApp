package com.example.myfirstapp.ui.add_itinerary_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirstapp.databinding.ItemItineraryLocoBinding
import com.example.myfirstapp.domain.entity.LocomotiveData

class LocoAdapter : RecyclerView.Adapter<LocoHolder>() {
    private val listLoco = mutableListOf<LocomotiveData>()

    fun setData(data: List<LocomotiveData>) {
        listLoco.clear()
        listLoco.addAll(data)
        notifyDataSetChanged()
    }

    fun emptyData() {
        listLoco.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocoHolder {
        val binding = ItemItineraryLocoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LocoHolder(binding)
    }

    override fun onBindViewHolder(holder: LocoHolder, position: Int) {
        holder.bind(listLoco[position])
    }

    override fun getItemCount(): Int = listLoco.size

}
