package com.example.myfirstapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirstapp.data.Itinerary
import com.example.myfirstapp.databinding.ItemBinding

class MainFragmentAdapter : RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {

    companion object {
        fun newInstance() = MainFragmentAdapter()
    }

    private var itineraryData: List<Itinerary> = listOf()

    fun setData(data: List<Itinerary>) {
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
        holder.bind(itineraryData[position])
    }

    override fun getItemCount() = itineraryData.size


    inner class MainViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itinerary: Itinerary) {
            binding.apply {

                // здесь необходимо написать присвоение значений в Item из класса Itinerary

                itemDay.text = itinerary.getAppearanceAtWork() // Day of month
                itemMonth.text = itinerary.getAppearanceAtWork() // Month
                itemTextTimeWork.text =
                    itinerary.getAppearanceAtWork() // time the Start and the End work
                itemTextWork.text =
                    itinerary.trainData.stationStart + " - " + itinerary.trainData.stationFinish
                itemTextOvertimeWork.text =
                    itinerary.getAppearanceAtWork() // Посчитать разницу ао времени
                itemTextLocomotive.text =
                    itinerary.locomotiveData.series + " №" + itinerary.locomotiveData.number


                root.setOnClickListener {
                    Toast.makeText(itemView.context, "Click ${itinerary.number}", Toast.LENGTH_LONG).show()
                }
            }

        }
    }
}