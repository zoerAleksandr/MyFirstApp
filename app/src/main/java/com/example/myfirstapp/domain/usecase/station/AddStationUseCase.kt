package com.example.myfirstapp.domain.usecase.station

import com.example.myfirstapp.domain.entity.Station
import com.example.myfirstapp.domain.repository.IRepositoryStation

class AddStationUseCase(private val repository: IRepositoryStation) {
    fun execute(station: Station): Long {
        return repository.addStation(station)
    }
}