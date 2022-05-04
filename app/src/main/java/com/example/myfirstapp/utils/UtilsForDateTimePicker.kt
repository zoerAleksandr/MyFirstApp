package com.example.myfirstapp.utils

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