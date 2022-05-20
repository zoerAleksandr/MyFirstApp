package com.example.myfirstapp.ui.add_loco_screen

sealed class StateAddLocoDieselFuel{
    data class Success(val result: Int): StateAddLocoDieselFuel()
    data class Error(val message: String?): StateAddLocoDieselFuel()
    data class EmptyData(val empty: Boolean): StateAddLocoDieselFuel()
}
