package com.example.myfirstapp.utils.convert_entity

import com.example.myfirstapp.data.room.entity.DieselFuelSectionRoomEntity
import com.example.myfirstapp.domain.entity.DieselFuelSection

fun toDieselFuelSectionList(roomEntityList: List<DieselFuelSectionRoomEntity>)
        : MutableList<DieselFuelSection> {
    val list = mutableListOf<DieselFuelSection>()
    for (item in roomEntityList) {
        list.add(toDieselFuelSection(item))
    }
    return list
}

fun toDieselFuelSection(roomEntity: DieselFuelSectionRoomEntity): DieselFuelSection {
    return DieselFuelSection(
        roomEntity.sectionId,
        roomEntity.locomotiveDataID,
        roomEntity.accepted,
        roomEntity.delivery,
        roomEntity.supply,
        roomEntity.consumption
    )
}

fun toDieselFuelSectionRoomEntity(entity: DieselFuelSection): DieselFuelSectionRoomEntity {
    return DieselFuelSectionRoomEntity(
        entity.sectionID,
        entity.locomotiveDataID,
        entity.accepted,
        entity.delivery,
        entity.supply,
        entity.consumption
    )
}