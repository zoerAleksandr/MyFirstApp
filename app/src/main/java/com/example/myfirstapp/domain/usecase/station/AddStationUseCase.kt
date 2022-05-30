package com.example.myfirstapp.domain.usecase.station

import com.example.myfirstapp.domain.entity.Station
import com.example.myfirstapp.domain.repository.IRepository
import io.reactivex.rxjava3.core.Single

class AddStationUseCase(private val repository: IRepository) {
    fun execute(station: Station): Single<Long> {
        return repository.addStation(station)
    }
}