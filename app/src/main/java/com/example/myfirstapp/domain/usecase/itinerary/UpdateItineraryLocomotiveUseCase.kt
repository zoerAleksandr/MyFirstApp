package com.example.myfirstapp.domain.usecase.itinerary

import com.example.myfirstapp.domain.entity.LocomotiveData
import com.example.myfirstapp.domain.repository.IRepository
import io.reactivex.rxjava3.core.Single

class UpdateItineraryLocomotiveUseCase(private val repository: IRepository) {
    fun execute(itineraryId: String, locomotiveData: MutableList<LocomotiveData>): Single<Int>{
        return repository.updateItineraryLocomotive(itineraryId, locomotiveData)
    }
}