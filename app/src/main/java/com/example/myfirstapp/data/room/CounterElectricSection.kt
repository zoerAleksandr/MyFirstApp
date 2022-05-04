package com.example.myfirstapp.data.room

import androidx.room.Entity

@Entity
data class CounterElectricSection(
    var takeEnergy: Int,
    var handOverEnergy: Int,
    var takeRecovery: Int,
    var handOverRecovery: Int,
)
