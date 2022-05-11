package com.example.myfirstapp.domain.entity

import java.util.*

data class Itinerary(
    val itineraryID: String,
    var number: String?,
    var appearanceAtWork: Calendar,
    var endOfWork: Calendar?,
    var restAtThePointOfTurnover: Boolean = true,
    var notes: String?,

    var followingByPassengerList: MutableList<String>,
    var locomotiveDataList: MutableList<String>,
    var trainDataList: MutableList<String>
)