package com.example.myfirstapp.domain.repository

import com.example.myfirstapp.domain.entity.Itinerary

interface IRepositoryItinerary {
    fun getListItinerary(): MutableList<Itinerary>
    fun getItinerary(itineraryID: String): Itinerary
    fun addItinerary(itinerary: Itinerary): Long
    fun deleteItinerary(itinerary: Itinerary): Int
    fun updateItinerary(itinerary: Itinerary): Int
}