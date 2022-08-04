package com.example.myfirstapp.data.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import androidx.room.util.TableInfo
import java.util.*

@Entity(
    tableName = "passenger",
    foreignKeys = [
        ForeignKey(
            entity = ItineraryRoomEntity::class,
            parentColumns = ["id"],
            childColumns = ["itineraryId"],
            onDelete = CASCADE,
            onUpdate = CASCADE
        )
    ],
    primaryKeys = ["itineraryId", "id"]
)
data class PassengerRoomEntity(
    val id: String,
    val itineraryId: String,
    var departureTime: Calendar?,
    var arrivalTime: Calendar?,
    var departureStation: String?,
    var arrivalStation: String?,
    var numberOfTrain: String?,
    var notes: String?
)


