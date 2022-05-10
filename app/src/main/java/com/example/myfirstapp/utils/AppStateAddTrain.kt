package com.example.myfirstapp.utils

import com.example.myfirstapp.data.room.entity.StationRoomEntity
import com.example.myfirstapp.domain.entity.Station

sealed class AppStateAddTrain {
    data class Success(val stationRoomEntity: MutableList<Station>) : AppStateAddTrain()
    data class Loading(val progress: Int?) : AppStateAddTrain()
    data class Error(val throwable: Throwable) : AppStateAddTrain()
}