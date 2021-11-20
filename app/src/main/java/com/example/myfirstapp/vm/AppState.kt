package com.example.myfirstapp.vm

import com.example.myfirstapp.data.Itinerary

sealed class AppState {
    class Success() : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}