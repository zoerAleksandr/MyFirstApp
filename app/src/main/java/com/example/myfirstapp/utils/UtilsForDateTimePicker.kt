package com.example.myfirstapp.utils

import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

fun getDatePicker(
    title: String,
    constraintBuilder: CalendarConstraints.Builder?
): MaterialDatePicker<Long> {
    return MaterialDatePicker.Builder.datePicker()
        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
        .setCalendarConstraints(constraintBuilder?.build())
        .setTitleText(title)
        .build()
}

fun getTimePicker(title: String, startTime: Calendar): MaterialTimePicker {
    return MaterialTimePicker.Builder()
        .setTimeFormat(TimeFormat.CLOCK_24H)
        .setTitleText(title)
        .setHour(startTime.get(Calendar.HOUR_OF_DAY))
        .setMinute(startTime.get(Calendar.MINUTE))
        .build()
}

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