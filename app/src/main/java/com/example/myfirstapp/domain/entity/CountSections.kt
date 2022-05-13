package com.example.myfirstapp.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class CountSections : Parcelable {
    @Parcelize
    object OneSection : CountSections()
    @Parcelize
    object TwoSection : CountSections()
    @Parcelize
    object ThreeSection : CountSections()
    @Parcelize
    object FourSection : CountSections()
}