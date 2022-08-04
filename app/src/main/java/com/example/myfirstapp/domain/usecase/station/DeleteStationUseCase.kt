package com.example.myfirstapp.domain.usecase.station

import com.example.myfirstapp.domain.entity.Station
import com.example.myfirstapp.domain.repository.IRepositoryStation

class DeleteStationUseCase(private val repository: IRepositoryStation) {
    fun execute(station: Station): Int {
        return repository.deleteStation(station)
    }
}