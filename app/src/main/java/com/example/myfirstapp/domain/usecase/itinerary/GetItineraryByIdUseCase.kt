package com.example.myfirstapp.domain.usecase.itinerary

import com.example.myfirstapp.domain.entity.Itinerary
import com.example.myfirstapp.domain.repository.IRepository
import io.reactivex.rxjava3.core.Single

class GetItineraryByIdUseCase(private val repository: IRepository) {
    fun execute(id: String): Single<Itinerary> {
        return repository.getItinerary(id)
    }
}