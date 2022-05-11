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
    @PrimaryKey()
    val followingByPassengerID: String,
    val itineraryID: String,
    var departureTime: Calendar?, // Calendar
    var arrivalTime: Calendar?, // Calendar
    var departureStation: String?,
    var arrivalStation: String?,
    var numberOfTrain: Int?
) {
//        fun createNewEntityFollowingByPassenger(followingByPassenger: FollowingByPassenger) =
//            FollowingByPassengerRoomEntity(
//                followingByPassengerID = followingByPassenger.followingByPassengerID,
//                itineraryID = followingByPassenger.itineraryID,
//                departureTime = followingByPassenger.departureTime,
//                arrivalTime = followingByPassenger.arrivalTime,
//                departureStation = followingByPassenger.departureStation,
//                arrivalStation = followingByPassenger.arrivalStation,
//                numberOfTrain = followingByPassenger.numberOfTrain,
//            )
//
//        fun toEntityFollowingByPassengerList(followingByPassengerList: MutableList<FollowingByPassenger?>): MutableList<FollowingByPassengerRoomEntity?> {
//            val list = mutableListOf<FollowingByPassengerRoomEntity?>()
//            for (item in followingByPassengerList) {
//                list.add(item?.let { createNewEntityFollowingByPassenger(it) })
//            }
//            return list
//        }

}


