package com.example.myfirstapp.data.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "train",
    foreignKeys = [
        ForeignKey(
            entity = ItineraryRoomEntity::class,
            parentColumns = ["id"],
            childColumns = ["itineraryId"],
            onDelete = CASCADE,
            onUpdate = CASCADE
        )
    ]
)
data class TrainDataRoomEntity(
    @PrimaryKey
    val id: String,
    val itineraryId: String,
    var numberOfTrain: Int?,
    var weight: Int?,
    var wheelAxle: Int?,
    var conditionalLength: Int?,
    var stations: MutableList<StationRoomEntity>
)
