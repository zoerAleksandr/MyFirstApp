package com.example.myfirstapp.data.room.entity

import androidx.room.Entity

@Entity
sealed class TypeOfTractionRoomEntity {
    object DieselLocomotive : TypeOfTractionRoomEntity()
    object ElectricLocomotive : TypeOfTractionRoomEntity()
}
