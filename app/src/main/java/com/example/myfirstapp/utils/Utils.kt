package com.example.myfirstapp.utils

import java.util.*

fun generateStringID(): String {
    return UUID.randomUUID().toString()
}

fun getOverTimeWork(timeInMillis: Long): String {
    val hours = timeInMillis / 3_600_000
    val x = timeInMillis % 3_600_000
    val minutes = x / 60_000
    return if (minutes != 0L) {
        "${hours}ч ${minutes}м"
    } else {
        "${hours}ч"
    }
}