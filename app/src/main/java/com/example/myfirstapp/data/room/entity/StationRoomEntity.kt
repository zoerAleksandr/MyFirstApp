package com.example.myfirstapp.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "station"
)
data class StationRoomEntity(
    @PrimaryKey()
    val stationID: String,
    val itineraryID: String,
    val trainDataID: String,
    var stationName: String?,
    var arrivalTime: String?, // Calendar
    var departureTime: String? // Calendar
)
