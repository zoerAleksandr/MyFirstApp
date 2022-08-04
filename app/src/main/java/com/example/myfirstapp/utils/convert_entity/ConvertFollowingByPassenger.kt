package com.example.myfirstapp.utils.convert_entity

import com.example.myfirstapp.data.room.entity.FollowingByPassengerRoomEntity
import com.example.myfirstapp.domain.entity.Passenger


fun toFollowingByPassengerList(
    roomEntityList: MutableList<FollowingByPassengerRoomEntity>
): MutableList<Passenger> {
    val list = mutableListOf<Passenger>()
    for (item in roomEntityList) {
        list.add(toFollowingByPassenger(item))
    }
    return list
}

fun toFollowingByPassenger(
    entityRoom: FollowingByPassengerRoomEntity
): Passenger {
    return Passenger(
        entityRoom.followingByPassengerID,
        entityRoom.itineraryID,
        entityRoom.departureTime,
        entityRoom.arrivalTime,
        entityRoom.departureStation,
        entityRoom.arrivalStation,
        entityRoom.numberOfTrain,
        entityRoom.notes
    )
}

fun toFollowingByPassengerRoomEntity(
    followingByPassenger: Passenger
): FollowingByPassengerRoomEntity {
    return FollowingByPassengerRoomEntity(
        followingByPassenger.followingByPassengerID,
        followingByPassenger.itineraryID,
        followingByPassenger.departureTime,
        followingByPassenger.arrivalTime,
        followingByPassenger.departureStation,
        followingByPassenger.arrivalStation,
        followingByPassenger.numberOfTrain,
        followingByPassenger.notes
    )
}
