package com.example.myfirstapp.data.room.entity

import androidx.room.Entity

@Entity
data class DieselFuelSectionRoomEntity(
    var takeFuel: Int,
    var handOverFuel: Int
)
