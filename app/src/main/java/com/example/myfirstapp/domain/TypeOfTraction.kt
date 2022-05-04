package com.example.myfirstapp.domain

import androidx.room.Entity

@Entity
sealed class TypeOfTraction {
    object DieselLocomotive : TypeOfTraction()
    object ElectricLocomotive : TypeOfTraction()
}
