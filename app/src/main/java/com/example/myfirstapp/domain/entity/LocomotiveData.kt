package com.example.myfirstapp.domain.entity

data class LocomotiveData(
    val locomotiveDataID: String,
    val itineraryID: String,
    var series: String?,
    var number: Byte?,
    var typeOfTraction: TypeOfTraction,
    var countSections: CountSections,

    var startAcceptance: String?, // Изменить тип на Calendar
    var endAcceptance: String?, // Изменить тип на Calendar
    var startDelivery: String?, // Изменить тип на Calendar
    var endDelivery: String?, // Изменить тип на Calendar

    var counterElectricListRoomEntity: MutableList<String>,
    var dieselFuelListRoomEntity: MutableList<String>,

    var countBrakeShoes: Int?,
    var countExtinguishers: Int?
)
