package com.example.myfirstapp.utils

import com.example.myfirstapp.domain.entity.Itinerary

sealed class AppState {
    data class Success(var list: MutableList<Itinerary>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}