package com.example.myfirstapp.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class CountSections(val index: Int) : Parcelable {
    @Parcelize
    object OneSection : CountSections(0)
    @Parcelize
    object TwoSection : CountSections(1)
    @Parcelize
    object ThreeSection : CountSections(2)
    @Parcelize
    object FourSection : CountSections(3)
}