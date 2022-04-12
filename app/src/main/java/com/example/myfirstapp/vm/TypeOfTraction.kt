package com.example.myfirstapp.vm

import androidx.room.Entity

@Entity
sealed class TypeOfTraction {
    object DieselLocomotive : TypeOfTraction()
    object ElectricLocomotive : TypeOfTraction()
}
