package com.example.myfirstapp.domain.entity

data class ElectricSection(
    val sectionID: String,
    val locomotiveDataId: String,
    var acceptanceEnergy: Int?,
    var deliveryEnergy: Int?,
    var consumptionEnergy: Int?,
    var acceptanceRecovery: Int?,
    var deliveryRecovery: Int?,
    var consumptionRecovery: Int?,
)
