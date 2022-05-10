package com.example.myfirstapp.utils.convert_entity

import com.example.myfirstapp.data.room.entity.LocomotiveDataRoomEntity
import com.example.myfirstapp.domain.entity.LocomotiveData

fun toLocomotiveDataList(
    roomEntityList: MutableList<LocomotiveDataRoomEntity>
): MutableList<LocomotiveData> {
    val list = mutableListOf<LocomotiveData>()
    for (item in roomEntityList) {
        list.add(toLocomotiveData(item))
    }
    return list
}

fun toLocomotiveData(roomEntity: LocomotiveDataRoomEntity): LocomotiveData {
    return LocomotiveData(
        roomEntity.locomotiveDataID,
        roomEntity.itineraryID,
        roomEntity.series,
        roomEntity.number,
        roomEntity.typeOfTraction,
        roomEntity.countSections,
        roomEntity.startAcceptance,
        roomEntity.endAcceptance,
        roomEntity.startDelivery,
        roomEntity.endDelivery,
        roomEntity.counterElectricListRoomEntity,
        roomEntity.dieselFuelListRoomEntity,
        roomEntity.countBrakeShoes,
        roomEntity.countExtinguishers
    )
}
fun toLocomotiveDataRoomEntity(locomotiveData: LocomotiveData): LocomotiveDataRoomEntity {
    return LocomotiveDataRoomEntity(
        locomotiveData.locomotiveDataID,
        locomotiveData.itineraryID,
        locomotiveData.series,
        locomotiveData.number,
        locomotiveData.typeOfTraction,
        locomotiveData.countSections,
        locomotiveData.startAcceptance,
        locomotiveData.endAcceptance,
        locomotiveData.startDelivery,
        locomotiveData.endDelivery,
        locomotiveData.counterElectricListRoomEntity,
        locomotiveData.dieselFuelListRoomEntity,
        locomotiveData.countBrakeShoes,
        locomotiveData.countExtinguishers
    )
}