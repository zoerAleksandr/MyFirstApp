package com.example.myfirstapp.ui.main_screen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirstapp.databinding.ItemBinding
import com.example.myfirstapp.domain.entity.Itinerary

class MainFragmentAdapter(
    private val clickListener: (Itinerary) -> Unit
) : RecyclerView.Adapter<MainViewHolder>() {

    private var itineraryData: MutableList<Itinerary> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: MutableList<Itinerary>) {
        itineraryData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = ItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        itineraryData[position].let { holder.bind(it, clickListener) }
    }

    override fun getItemCount() = itineraryData.size
}