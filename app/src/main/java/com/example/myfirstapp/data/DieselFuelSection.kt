package com.example.myfirstapp.data

import androidx.room.Entity

@Entity
data class DieselFuelSection(
    var takeFuel: Int,
    var handOverFuel: Int
)
