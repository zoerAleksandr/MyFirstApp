package com.example.myfirstapp.vm

import androidx.room.Entity

@Entity
sealed class CountSections {
    object OneSection : CountSections()
    object TwoSection : CountSections()
    object ThreeSection : CountSections()
    object FourSection : CountSections()
}
