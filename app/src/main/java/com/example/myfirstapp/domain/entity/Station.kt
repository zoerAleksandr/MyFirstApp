package com.example.myfirstapp.domain.entity

data class Station(
    val StationID: Long,
    val itineraryID: Long,
    val trainDataID: Long,
    var stationName: String?,
    var arrivalTime: String?, // Calendar
    var departureTime: String? // Calendar
)
