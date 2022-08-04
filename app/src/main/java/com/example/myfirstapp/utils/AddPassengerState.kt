package com.example.myfirstapp.utils

import com.example.myfirstapp.domain.entity.Passenger

sealed class AddPassengerState {
    data class Success(val passenger: Passenger) : AddPassengerState()
    data class Loading(val progress: Int?) : AddPassengerState()
    data class Error(val throwable: Throwable) : AddPassengerState()
}