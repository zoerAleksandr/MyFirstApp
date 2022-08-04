package com.example.myfirstapp.domain.usecase.itinerary

import com.example.myfirstapp.domain.entity.Itinerary
import com.example.myfirstapp.domain.repository.IRepositoryItinerary

class GetItineraryByIdUseCase(private val repository: IRepositoryItinerary) {
    fun execute(id: String): Itinerary {
        return repository.getItinerary(id)
    }
}