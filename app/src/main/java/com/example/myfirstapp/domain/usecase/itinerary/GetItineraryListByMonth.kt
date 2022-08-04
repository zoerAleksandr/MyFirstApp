package com.example.myfirstapp.domain.usecase.itinerary

import com.example.myfirstapp.domain.entity.Itinerary
import com.example.myfirstapp.domain.repository.IRepositoryItinerary

class GetItineraryListByMonth(private val repository: IRepositoryItinerary) {
    fun execute(month: Int): MutableList<Itinerary> {
        return repository.getListItinerary()
    }
}