package com.example.myfirstapp.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "station"
)
data class StationRoomEntity(
    @PrimaryKey
    val stationID: String,
    val trainDataID: String,
    var stationName: String?,
    var arrivalTime: Calendar?,
    var departureTime: Calendar?
)
