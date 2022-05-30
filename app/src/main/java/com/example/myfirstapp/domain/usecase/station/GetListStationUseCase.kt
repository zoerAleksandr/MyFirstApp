package com.example.myfirstapp.domain.usecase.station

import com.example.myfirstapp.domain.entity.Station
import com.example.myfirstapp.domain.repository.IRepository
import io.reactivex.rxjava3.core.Single

class GetListStationUseCase(private val repository: IRepository) {
    fun execute(trainDataId: String): Single<MutableList<Station>> {
        return repository.getListStation(trainDataId)
    }
}