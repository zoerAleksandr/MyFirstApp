package com.example.myfirstapp.ui.add_itinerary_screen

sealed class ListState {
    object Empty : ListState()
    object Loading : ListState()
    data class Success<T>(val list: List<T>) : ListState()
    data class Error(val error: Throwable) : ListState()
}
