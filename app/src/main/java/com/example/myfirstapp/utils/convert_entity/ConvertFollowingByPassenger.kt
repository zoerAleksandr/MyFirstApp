package com.example.myfirstapp.utils.convert_entity

import com.example.myfirstapp.data.room.entity.PassengerRoomEntity
import com.example.myfirstapp.domain.entity.Passenger


fun toPassengerList(
    roomEntityList: MutableList<PassengerRoomEntity>
): MutableList<Passenger> {
    val list = mutableListOf<Passenger>()
    for (item in roomEntityList) {
        list.add(toPassenger(item))
    }
    return list
}

fun toPassengerRoomEntityList(passengerList: List<Passenger>): MutableList<PassengerRoomEntity> {
    val list = mutableListOf<PassengerRoomEntity>()
    for (item in passengerList) {
        list.add(toPassengerRoomEntity(item))
    }
    return list
}

fun toPassenger(
    entityRoom: PassengerRoomEntity
): Passenger {
    return Passenger(
        entityRoom.id,
        entityRoom.itineraryId,
        entityRoom.departureTime,
        entityRoom.arrivalTime,
        entityRoom.departureStation,
        entityRoom.arrivalStation,
        entityRoom.numberOfTrain,
        entityRoom.notes
    )
}

fun toPassengerRoomEntity(
    followingByPassenger: Passenger
): PassengerRoomEntity {
    return PassengerRoomEntity(
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
