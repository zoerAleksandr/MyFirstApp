package com.example.myfirstapp.domain.usecase.itinerary

import com.example.myfirstapp.domain.entity.Itinerary
import com.example.myfirstapp.domain.repository.IRepository
import io.reactivex.rxjava3.core.Single

class AddItineraryUseCase(private val repository: IRepository) {
    fun execute(itinerary: Itinerary): Single<Long>{
        return repository.addItinerary(itinerary)
    }
}