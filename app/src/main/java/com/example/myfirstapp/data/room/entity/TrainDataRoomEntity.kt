package com.example.myfirstapp.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myfirstapp.domain.entity.Station

@Entity(
    tableName = "train"
)
data class TrainDataRoomEntity(
    @PrimaryKey
    val trainDataID: String,
    val itineraryID: String,
    var numberOfTrain: Int?,
    var weight: Int?,
    var wheelAxle: Int?,
    var conditionalLength: Int?,
    var stations: MutableList<Station>
)
