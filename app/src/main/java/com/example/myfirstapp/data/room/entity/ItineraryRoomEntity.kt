package com.example.myfirstapp.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myfirstapp.domain.entity.LocomotiveData
import com.example.myfirstapp.domain.entity.Passenger
import com.example.myfirstapp.domain.entity.TrainData
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
    var notes: String?,
    @ColumnInfo(name = "following_by_passenger_list")
    var followingByPassengerList: MutableList<PassengerRoomEntity>,
    @ColumnInfo(name = "locomotive_data_list")
    var locomotiveDataList: MutableList<LocomotiveDataRoomEntity>,
    @ColumnInfo(name = "train_data_list")
    var trainDataList: MutableList<TrainDataRoomEntity>
)