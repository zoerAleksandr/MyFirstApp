package com.example.myfirstapp.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "dieselSection"
)
data class DieselFuelSectionRoomEntity(
    @PrimaryKey
    val sectionId: String,
    val locomotiveDataID: String,
    var accepted: Int?,
    var delivery: Int?,
    var supply: Int?
)
