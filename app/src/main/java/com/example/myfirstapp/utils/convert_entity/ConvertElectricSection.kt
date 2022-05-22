package com.example.myfirstapp.utils.convert_entity

import com.example.myfirstapp.data.room.entity.ElectricSectionRoomEntity
import com.example.myfirstapp.domain.entity.ElectricSection

fun toElectricSectionList(roomEntityList: List<ElectricSectionRoomEntity>)
        : MutableList<ElectricSection> {
    val list = mutableListOf<ElectricSection>()
    for (item in roomEntityList) {
        list.add(toElectricSection(item))
    }
    return list
}

fun toElectricSection(roomEntity: ElectricSectionRoomEntity): ElectricSection {
    return ElectricSection(
        roomEntity.sectionID,
        roomEntity.locomotiveDataId,
        roomEntity.acceptanceEnergy,
        roomEntity.deliveryEnergy,
        roomEntity.consumptionEnergy,
        roomEntity.acceptanceRecovery,
        roomEntity.deliveryRecovery,
        roomEntity.consumptionRecovery
    )
}

fun toElectricSectionRoomEntity(entity: ElectricSection): ElectricSectionRoomEntity {
    return ElectricSectionRoomEntity(
        entity.sectionID,
        entity.locomotiveDataId,
        entity.acceptanceEnergy,
        entity.deliveryEnergy,
        entity.consumptionEnergy,
        entity.acceptanceRecovery,
        entity.deliveryRecovery,
        entity.consumptionRecovery,
    )
}