package com.example.myfirstapp.utils

import com.example.myfirstapp.domain.entity.LocomotiveData

sealed class AddLocoState {
    data class Success(val locomotive: LocomotiveData) : AddLocoState()
    data class Loading(val progress: Int?) : AddLocoState()
    data class Error(val throwable: Throwable) : AddLocoState()
}
