package com.example.myfirstapp.domain.entity

sealed class CountSections {
    object OneSection : CountSections()
    object TwoSection : CountSections()
    object ThreeSection : CountSections()
    object FourSection : CountSections()
}
