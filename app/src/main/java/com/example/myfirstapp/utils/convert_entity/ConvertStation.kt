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
        roomEntity.id,
        roomEntity.trainDataId,
        roomEntity.stationName,
        roomEntity.arrivalTime,
        roomEntity.departureTime
    )
}

fun toStationRoomEntityList(stationList: List<Station>): MutableList<StationRoomEntity> {
    val list = mutableListOf<StationRoomEntity>()
    for (item in stationList) {
        list.add(toStationRoomEntity(item))
    }
    return list
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