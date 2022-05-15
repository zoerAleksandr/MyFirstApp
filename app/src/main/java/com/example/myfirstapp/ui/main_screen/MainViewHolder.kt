package com.example.myfirstapp.ui.main_screen

import android.content.res.Resources
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirstapp.R
import com.example.myfirstapp.databinding.ItemBinding
import com.example.myfirstapp.domain.entity.Itinerary
import com.example.myfirstapp.utils.getOverTimeWork
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

class MainViewHolder(
    private val binding: ItemBinding,
) : RecyclerView.ViewHolder(binding.root), KoinComponent {
    private val resources: Resources by inject()
    fun bind(
        itinerary: Itinerary,
        clickListener: (Itinerary) -> Unit
    ) {
        val startMonth = itinerary.appearanceAtWork?.get(Calendar.MONTH)?.plus(1).toString()
        val startDay = itinerary.appearanceAtWork?.get(Calendar.DAY_OF_MONTH).toString()
        val startHour = itinerary.appearanceAtWork?.get(Calendar.HOUR_OF_DAY).toString()
        val startMinute = itinerary.appearanceAtWork?.get(Calendar.MINUTE).toString()

        val endHour = itinerary.endOfWork?.get(Calendar.HOUR_OF_DAY).toString()
        val endMinute = itinerary.endOfWork?.get(Calendar.MINUTE).toString()


        binding.apply {
            if(itinerary.appearanceAtWork != null) {
                itemDay.alpha = 1f
                itemMonth.alpha = 1f
                itemDay.text = if (startDay.length == 1) "0$startDay" else startDay
                itemMonth.text = if (startMonth.length == 1) "0$startMonth" else startMonth
                itemTextTimeAppearanceWork.alpha = 1f
                itemTextTimeAppearanceWork.text =
                    resources.getString(R.string.text_through_defise, startHour, startMinute)
            }

            if (itinerary.endOfWork != null) {
                itemTextTimeEndingWork.text =
                    resources.getString(R.string.text_through_defise, endHour, endMinute)
                itemTextOvertimeWork.alpha = 1f
                itemTextOvertimeWork.text = getOverTimeWork(itinerary.getOverTimeMillis())
            }

            if (itinerary.trainDataList.isNotEmpty()) {
                itemTextWork.text =
                    itinerary.trainDataList[0].stations?.let { stations ->
                        resources.getString(
                            R.string.text_through_defise,
                            stations.first().stationName,
                            stations.last().stationName
                        )
                    }
            }

            if (itinerary.locomotiveDataList.isNotEmpty()) {
                itemTextLocomotive.text =
                    resources.getString(
                        R.string.text_locomotive_data,
                        itinerary.locomotiveDataList[0].series.toString(),
                        itinerary.locomotiveDataList[0].number.toString()
                    )
            }

            root.setOnClickListener {
                clickListener.invoke(itinerary)
            }
        }
    }
}