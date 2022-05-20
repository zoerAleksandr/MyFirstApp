package com.example.myfirstapp.domain.entity

data class DieselFuelSection(
    val sectionID: String,
    val locomotiveDataID: String,
    var accepted: Int?,
    var delivery: Int?,
    var supply: Int?,
    var consumption: Int?
)
