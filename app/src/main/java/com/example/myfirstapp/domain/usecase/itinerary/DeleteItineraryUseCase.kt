package com.example.myfirstapp.domain.usecase.itinerary

import com.example.myfirstapp.domain.entity.Itinerary
import com.example.myfirstapp.domain.repository.IRepositoryItinerary

class DeleteItineraryUseCase(private val repository: IRepositoryItinerary) {
    fun execute(itinerary: Itinerary): Int {
        return repository.deleteItinerary(itinerary)
    }
}