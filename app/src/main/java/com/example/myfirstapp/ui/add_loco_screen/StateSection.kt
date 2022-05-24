package com.example.myfirstapp.ui.add_loco_screen

sealed class StateSection{
    data class Success(val result: Int): StateSection()
    data class Error(val message: String?): StateSection()
    data class EmptyData(val empty: Boolean): StateSection()
}
