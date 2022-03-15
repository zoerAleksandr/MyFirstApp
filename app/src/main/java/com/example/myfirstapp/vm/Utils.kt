package com.example.myfirstapp.vm

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import com.example.myfirstapp.R
import com.google.android.material.timepicker.MaterialTimePicker
import java.util.*


fun setTextTime(picker: MaterialTimePicker): String {
    val hour = if (picker.hour.toString().length == 1) {
        "0${picker.hour}"
    } else {
        "${picker.hour}"
    }
    val minute = if (picker.minute.toString().length == 1) {
        "0${picker.minute}"
    } else {
        "${picker.minute}"
    }
    return "$hour:$minute"
}

fun setTextDate(time: Long): String {
    val calendar = Calendar.getInstance().apply {
        timeInMillis = time
    }
    val day = if (calendar.get(Calendar.DAY_OF_MONTH).toString().length == 1) {
        "0${calendar.get(Calendar.DAY_OF_MONTH)}"
    } else {
        "${calendar.get(Calendar.DAY_OF_MONTH)}"
    }
    val month = if (calendar.get(Calendar.MONTH).toString().length == 1) {
        "0${calendar.get(Calendar.MONTH) + 1}"
    } else {
        "${calendar.get(Calendar.MONTH) + 1}"
    }
    val year: String = "${calendar.get(Calendar.YEAR)}".substring(2)

    return "$day.$month.$year"
}

@SuppressLint("UseCompatLoadingForDrawables")
fun setErrorBackground(context: Context, view: View, color: ColorForBackgroundError) {
    view.background = context.resources.getDrawable(
        when (color) {
            ColorForBackgroundError.RED -> {
                R.drawable.shape_background_data_block_error_red
            }
            ColorForBackgroundError.YELLOW -> {
                R.drawable.shape_background_data_block_error_yellow
            }
        }, context.theme
    )
}

@SuppressLint("UseCompatLoadingForDrawables")
fun setDefaultBackground(context: Context, view: View) {
    view.background = context.resources.getDrawable(
        R.drawable.shape_background_data_block,
        context.theme
    )
}