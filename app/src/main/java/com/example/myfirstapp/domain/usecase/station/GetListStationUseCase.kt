package com.example.myfirstapp.domain.usecase.station

import com.example.myfirstapp.domain.entity.Station
import com.example.myfirstapp.domain.repository.IRepositoryStation

class GetListStationUseCase(private val repository: IRepositoryStation) {
    fun execute(trainDataId: String): MutableList<Station> {
        return repository.getListStation(trainDataId)
    }
}