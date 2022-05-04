package com.example.myfirstapp.domain.entity

sealed class TypeOfTraction {
    object DieselLocomotive : TypeOfTraction()
    object ElectricLocomotive : TypeOfTraction()
}
