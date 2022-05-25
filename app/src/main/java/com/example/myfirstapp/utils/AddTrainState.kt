package com.example.myfirstapp.utils

import com.example.myfirstapp.domain.entity.Station

sealed class AddTrainState {
    data class Success(val station: Station) : AddTrainState()
    data class Loading(val progress: Int?) : AddTrainState()
    data class Error(val throwable: Throwable) : AddTrainState()
}