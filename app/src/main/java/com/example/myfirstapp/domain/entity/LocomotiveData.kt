package com.example.myfirstapp.domain.entity

import com.example.myfirstapp.data.room.entity.CounterElectricSectionRoomEntity
import com.example.myfirstapp.data.room.entity.DieselFuelSectionRoomEntity

data class LocomotiveData(
    val locomotiveDataID: Long,
    val itineraryID: Long,
    var series: String?,
    var number: Byte?,
    var typeOfTraction: TypeOfTraction,
    var countSections: CountSections,

    var startAcceptance: String?, // Изменить тип на Calendar
    var endAcceptance: String?, // Изменить тип на Calendar
    var startDelivery: String?, // Изменить тип на Calendar
    var endDelivery: String?, // Изменить тип на Calendar

    var counterElectricListRoomEntity: MutableList<CounterElectricSectionRoomEntity>?,
    var dieselFuelListRoomEntity: MutableList<DieselFuelSectionRoomEntity>?,

    var countBrakeShoes: Int?,
    var countExtinguishers: Int?
)
