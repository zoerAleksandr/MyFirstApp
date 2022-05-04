package com.example.myfirstapp.domain

import com.example.myfirstapp.data.room.CounterElectricSection
import com.example.myfirstapp.data.room.DieselFuelSection

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

    var counterElectricList: MutableList<CounterElectricSection>?,
    var dieselFuelList: MutableList<DieselFuelSection>?,

    var countBrakeShoes: Int?,
    var countExtinguishers: Int?
)
