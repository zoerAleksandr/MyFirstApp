package com.example.myfirstapp.data.room.entity

import androidx.room.Entity

@Entity
data class CounterElectricSectionRoomEntity(
    var takeEnergy: Int,
    var handOverEnergy: Int,
    var takeRecovery: Int,
    var handOverRecovery: Int,
)
