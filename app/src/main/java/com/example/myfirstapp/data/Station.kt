package com.example.myfirstapp.data

import java.util.*

data class Station(
    // ID
    val StationID: Long,
    // itineraryID
    val itineraryID: Long,
    // trainDataID
    val trainDataID: Long,
    // название станции
    var stationName: String?,
    // время прибытия
    var arrivalTime: String?, // Calendar
    // время отправления
    var departureTime: String? // Calendar
)
