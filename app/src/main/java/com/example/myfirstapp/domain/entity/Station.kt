package com.example.myfirstapp.domain.entity

data class Station(
    val stationID: String,
    val itineraryID: String,
    val trainDataID: String,
    var stationName: String?,
    var arrivalTime: String?, // Calendar
    var departureTime: String? // Calendar
)
