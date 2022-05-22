package com.example.myfirstapp.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "electricSection"
)
data class ElectricSectionRoomEntity(
    @PrimaryKey
    val sectionID: String,
    val locomotiveDataId: String,
    var acceptanceEnergy: Int?,
    var deliveryEnergy: Int?,
    var consumptionEnergy: Int?,
    var acceptanceRecovery: Int?,
    var deliveryRecovery: Int?,
    var consumptionRecovery: Int?
)
