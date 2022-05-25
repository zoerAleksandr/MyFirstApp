package com.example.myfirstapp.domain.entity

import java.util.*

data class Station(
    val stationID: String,
    val trainDataID: String,
    var stationName: String?,
    var arrivalTime: Calendar?,
    var departureTime: Calendar?
)
