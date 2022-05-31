package com.example.myfirstapp.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.myfirstapp.domain.entity.FollowingByPassenger
import com.google.gson.Gson
import java.util.*

@Entity(
    tableName = "passenger"
)
data class FollowingByPassengerRoomEntity(
    @PrimaryKey
    val followingByPassengerID: String,
    val itineraryID: String,
    var departureTime: Calendar?,
    var arrivalTime: Calendar?,
    var departureStation: String?,
    var arrivalStation: String?,
    var numberOfTrain: String?,
    var notes: String?
)


