package com.example.myfirstapp.domain.entity

import java.util.*

data class LocomotiveData(
    val locomotiveDataID: String,
    val itineraryID: String,
    var series: String?,
    var number: Byte?,
    var typeOfTraction: TypeOfTraction,
    var countSections: CountSections,

    var startAcceptance: Calendar?,
    var endAcceptance: Calendar?,
    var startDelivery: Calendar?,
    var endDelivery: Calendar?,

    var counterElectricListRoomEntity: MutableList<String>,
    var dieselFuelListRoomEntity: MutableList<String>,

    var countBrakeShoes: Int?,
    var countExtinguishers: Int?
)
