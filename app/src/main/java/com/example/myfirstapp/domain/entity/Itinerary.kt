package com.example.myfirstapp.domain.entity

import java.util.*

data class Itinerary(
    val itineraryID: String,
    var number: String?,
    var appearanceAtWork: Calendar,
    var endOfWork: Calendar?,
    var restAtThePointOfTurnover: Boolean,
    var notes: String?,

    var followingByPassengerList: MutableList<FollowingByPassenger>,
    var locomotiveDataList: MutableList<LocomotiveData>,
    var trainDataList: MutableList<TrainData>
) {
    fun getOverTimeMillis(): Long {
        val timeStart = this.appearanceAtWork.timeInMillis
        val timeEnd = this.endOfWork?.timeInMillis
        return timeEnd?.minus(timeStart) ?: 0L
    }
}