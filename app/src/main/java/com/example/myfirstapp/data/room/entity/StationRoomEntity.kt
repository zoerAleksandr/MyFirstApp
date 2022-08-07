package com.example.myfirstapp.data.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import java.util.*

@Entity(
    tableName = "station",
    foreignKeys = [
        ForeignKey(
            entity = TrainDataRoomEntity::class,
            parentColumns = ["id"],
            childColumns = ["trainDataId"],
            onDelete = CASCADE,
            onUpdate = CASCADE
        )
    ],
    primaryKeys = ["trainDataId", "id"]
)
data class StationRoomEntity(
    val id: String,
    val trainDataId: String,
    var stationName: String?,
    var arrivalTime: Calendar?,
    var departureTime: Calendar?
)
