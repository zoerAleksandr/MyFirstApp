package com.example.myfirstapp.domain.entity

import java.util.*

data class LocomotiveData(
    val locomotiveDataID: String,
    val itineraryID: String,
    var series: String?,
    var number: String?,
    var typeOfTraction: TypeOfTraction,
    var countSections: CountSections,

    var startAcceptance: Calendar?,
    var endAcceptance: Calendar?,
    var startDelivery: Calendar?,
    var endDelivery: Calendar?,

    var electricSectionList: MutableList<ElectricSection>,
    var dieselFuelSectionList: MutableList<DieselFuelSection>,

    var countBrakeShoes: Int?,
    var countExtinguishers: Int?
)
