package com.example.myfirstapp.vm

import com.example.myfirstapp.data.Itinerary

sealed class AppState {
    data class Success(var list: MutableList<Itinerary?>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}