package com.example.myfirstapp.vm

import com.example.myfirstapp.room.EntityStation

sealed class AppStateAddTrain {
    data class Success(val station: List<EntityStation>) : AppStateAddTrain()
    data class Loading(val progress: Int?) : AppStateAddTrain()
    data class Error(val throwable: Throwable) : AppStateAddTrain()
}