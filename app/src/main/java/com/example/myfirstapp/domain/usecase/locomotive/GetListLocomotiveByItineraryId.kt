package com.example.myfirstapp.domain.usecase.locomotive

import com.example.myfirstapp.domain.entity.LocomotiveData
import com.example.myfirstapp.domain.repository.IRepositoryLocomotive

class GetListLocomotiveByItineraryId(private val repository: IRepositoryLocomotive) {
    fun execute(itineraryId: String): List<LocomotiveData> {
        return repository.getListLocomotiveData(itineraryId)
    }
}