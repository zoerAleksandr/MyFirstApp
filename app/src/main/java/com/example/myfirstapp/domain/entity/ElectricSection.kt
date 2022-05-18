package com.example.myfirstapp.domain.entity

data class ElectricSection(
    val sectionID: String,
    val locomotiveDataId: String,
    var acceptanceEnergy: Int?,
    var deliveryEnergy: Int?,
    var acceptanceRecovery: Int?,
    var deliveryRecovery: Int?
)
