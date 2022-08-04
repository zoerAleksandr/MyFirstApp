package com.example.myfirstapp.domain.usecase.station

import com.example.myfirstapp.domain.entity.Station
import com.example.myfirstapp.domain.repository.IRepositoryStation

class GetStationUseCase(private val repository: IRepositoryStation) {
    fun execute(stationId: String): Station {
        return repository.getStation(stationId)
    }
}