package com.example.myfirstapp.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "itinerary"
)
data class ItineraryRoomEntity(
    @PrimaryKey
    val id: String,
    var number: String?,
    @ColumnInfo(name = "appearance_at_work")
    var appearanceAtWork: Calendar?,
    @ColumnInfo(name = "end_at_work")
    var endOfWork: Calendar?,
    @ColumnInfo(name = "rest_at_the_point_of_turnover")
    var restAtThePointOfTurnover: Boolean,
    var notes: String?
)