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
        locomotiveDataID = roomEntity.locomotiveDataID,
        itineraryID = roomEntity.itineraryID,
        series = roomEntity.series,
        number = roomEntity.number,
        typeOfTraction = roomEntity.typeOfTraction,
        countSections = roomEntity.countSections,
        startAcceptance = roomEntity.startAcceptance,
        endAcceptance = roomEntity.endAcceptance,
        startDelivery = roomEntity.startDelivery,
        endDelivery = roomEntity.endDelivery,
        electricSectionList = roomEntity.electricSectionList,
        dieselFuelSectionList = roomEntity.dieselFuelSectionList,
        countBrakeShoes = roomEntity.countBrakeShoes,
        countExtinguishers = roomEntity.countExtinguishers
    )
}
fun toLocomotiveDataRoomEntity(locomotiveData: LocomotiveData): LocomotiveDataRoomEntity {
    return LocomotiveDataRoomEntity(
        locomotiveDataID = locomotiveData.locomotiveDataID,
        itineraryID = locomotiveData.itineraryID,
        series = locomotiveData.series,
        number = locomotiveData.number,
        typeOfTraction = locomotiveData.typeOfTraction,
        countSections = locomotiveData.countSections,
        startAcceptance = locomotiveData.startAcceptance,
        endAcceptance = locomotiveData.endAcceptance,
        startDelivery = locomotiveData.startDelivery,
        endDelivery = locomotiveData.endDelivery,
        electricSectionList = locomotiveData.electricSectionList,
        dieselFuelSectionList = locomotiveData.dieselFuelSectionList,
        countBrakeShoes = locomotiveData.countBrakeShoes,
        countExtinguishers = locomotiveData.countExtinguishers
    )
}