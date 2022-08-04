package com.example.myfirstapp.utils.convert_entity

import com.example.myfirstapp.data.room.entity.StationRoomEntity
import com.example.myfirstapp.domain.entity.Station


fun toStationList(
    roomEntityList: MutableList<StationRoomEntity>
): MutableList<Station> {
    val list = mutableListOf<Station>()
    for (item in roomEntityList) {
        list.add(toStation(item))
    }
    return list
}

fun toStation(roomEntity: StationRoomEntity): Station {
    return Station(
        roomEntity.stationID,
        roomEntity.trainDataID,
        roomEntity.stationName,
        roomEntity.arrivalTime,
        roomEntity.departureTime
    )
}

fun toStationRoomEntity(station: Station): StationRoomEntity {
    return StationRoomEntity(
        station.stationID,
        station.trainDataID,
        station.stationName,
        station.arrivalTime,
        station.departureTime
    )
}