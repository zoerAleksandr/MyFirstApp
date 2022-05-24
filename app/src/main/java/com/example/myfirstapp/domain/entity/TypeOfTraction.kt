package com.example.myfirstapp.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class TypeOfTraction(val typeText: String) : Parcelable {
    @Parcelize
    object DieselLocomotive : TypeOfTraction("Diesel")

    @Parcelize
    object ElectricLocomotive : TypeOfTraction("Electric")
}
