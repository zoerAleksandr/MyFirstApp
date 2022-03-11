package com.example.myfirstapp.vm

sealed class CountSections {
    object OneSection : CountSections()
    object TwoSection : CountSections()
    object ThreeSection : CountSections()
}
