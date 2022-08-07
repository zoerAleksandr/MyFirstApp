package com.example.myfirstapp.domain.entity

import java.util.*

data class Itinerary(
    val itineraryID: String,
    var number: String?,
    var appearanceAtWork: Calendar?,
    var endOfWork: Calendar?,
    var restAtThePointOfTurnover: Boolean,
    var notes: String?
) {
    fun getOverTimeMillis(): Long {
        return if (appearanceAtWork != null && endOfWork != null) {
            val timeStart = this.appearanceAtWork!!.timeInMillis
            val timeEnd = this.endOfWork!!.timeInMillis
            timeEnd.minus(timeStart)
        } else {
            0L
        }
    }
}