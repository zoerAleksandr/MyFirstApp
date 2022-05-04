package com.example.myfirstapp.data.room

import androidx.room.Entity

@Entity
data class DieselFuelSection(
    var takeFuel: Int,
    var handOverFuel: Int
)
