package com.example.myfirstapp.utils.convert_entity

import com.example.myfirstapp.data.room.entity.*
import com.example.myfirstapp.domain.entity.*


fun toFollowingByPassengerList(
    roomEntityList: MutableList<FollowingByPassengerRoomEntity>
): MutableList<FollowingByPassenger> {
    val list = mutableListOf<FollowingByPassenger>()
    for (item in roomEntityList) {
        list.add(toFollowingByPassenger(item))
    }
    return list
}

fun toFollowingByPassenger(
    entityRoom: FollowingByPassengerRoomEntity
): FollowingByPassenger {
    return FollowingByPassenger(
        entityRoom.followingByPassengerID,
        entityRoom.itineraryID,
        entityRoom.departureTime,
        entityRoom.arrivalTime,
        entityRoom.departureStation,
        entityRoom.arrivalStation,
        entityRoom.numberOfTrain,
    )
}
fun toFollowingByPassengerRoomEntity(
    followingByPassenger: FollowingByPassenger
): FollowingByPassengerRoomEntity {
    return FollowingByPassengerRoomEntity(
        followingByPassenger.followingByPassengerID,
        followingByPassenger.itineraryID,
        followingByPassenger.departureTime,
        followingByPassenger.arrivalTime,
        followingByPassenger.departureStation,
        followingByPassenger.arrivalStation,
        followingByPassenger.numberOfTrain,
    )
}
