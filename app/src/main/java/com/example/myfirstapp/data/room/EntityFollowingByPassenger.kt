package com.example.myfirstapp.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.myfirstapp.domain.FollowingByPassenger
import com.google.gson.Gson

@Entity(
    tableName = "passenger"
)
data class EntityFollowingByPassenger(
    @PrimaryKey
    val followingByPassengerID: Long = 0L,
    val itineraryID: Long = 0L,
    var departureTime: String?, // Calendar
    var arrivalTime: String?, // Calendar
    var departureStation: String?,
    var arrivalStation: String?,
    var numberOfTrain: Int?
) {
    companion object {
        fun createNewEntityFollowingByPassenger(followingByPassenger: FollowingByPassenger) =
            EntityFollowingByPassenger(
                followingByPassengerID = followingByPassenger.followingByPassengerID,
                itineraryID = followingByPassenger.itineraryID,
                departureTime = followingByPassenger.departureTime,
                arrivalTime = followingByPassenger.arrivalTime,
                departureStation = followingByPassenger.departureStation,
                arrivalStation = followingByPassenger.arrivalStation,
                numberOfTrain = followingByPassenger.numberOfTrain,
            )

        fun toEntityFollowingByPassengerList(followingByPassengerList: MutableList<FollowingByPassenger?>): MutableList<EntityFollowingByPassenger?> {
            val list = mutableListOf<EntityFollowingByPassenger?>()
            for (item in followingByPassengerList) {
                list.add(item?.let { createNewEntityFollowingByPassenger(it) })
            }
            return list
        }

        fun toFollowingByPassengerList(followingByPassengerList: MutableList<EntityFollowingByPassenger?>): MutableList<FollowingByPassenger?> {
            val list = mutableListOf<FollowingByPassenger?>()
            for (item in followingByPassengerList) {
                if (item != null) {
                    list.add(item.toFollowingByPassenger())
                }
            }
            return list
        }
    }

    fun toFollowingByPassenger(): FollowingByPassenger = FollowingByPassenger(
        0,
        itineraryID = itineraryID,
        departureTime = departureTime,
        arrivalTime = arrivalTime,
        departureStation = departureStation,
        arrivalStation = arrivalStation,
        numberOfTrain = numberOfTrain,
    )
}

class ConvertersFollowingByPassenger {
    @TypeConverter
    fun listToJson(value: List<EntityFollowingByPassenger>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Array<EntityFollowingByPassenger>::class.java).toList()
}

