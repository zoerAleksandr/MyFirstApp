package com.example.myfirstapp.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class TypeOfTraction : Parcelable {
    @Parcelize
    object DieselLocomotive : TypeOfTraction()

    @Parcelize
    object ElectricLocomotive : TypeOfTraction()
}
