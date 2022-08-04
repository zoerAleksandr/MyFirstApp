package com.example.myfirstapp.domain.usecase.itinerary

import com.example.myfirstapp.domain.entity.Itinerary
import com.example.myfirstapp.domain.repository.IRepositoryItinerary

class AddItineraryUseCase(private val repository: IRepositoryItinerary) {
    fun execute(itinerary: Itinerary): Long {
        return repository.addItinerary(itinerary)
    }
}