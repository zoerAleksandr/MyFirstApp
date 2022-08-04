package com.example.myfirstapp.data.room.repository

import com.example.myfirstapp.data.room.ItineraryDAO
import com.example.myfirstapp.domain.entity.Itinerary
import com.example.myfirstapp.domain.repository.IRepositoryItinerary
import com.example.myfirstapp.utils.convert_entity.toItinerary
import com.example.myfirstapp.utils.convert_entity.toItineraryList
import com.example.myfirstapp.utils.convert_entity.toItineraryRoomEntity
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RoomRepositoryItinerary : IRepositoryItinerary, KoinComponent {
    private val localStorage: ItineraryDAO by inject()

    override fun addItinerary(itinerary: Itinerary): Long {
        return localStorage.addItinerary(
            toItineraryRoomEntity(itinerary)
        )
    }

    override fun getListItinerary(): MutableList<Itinerary> {
        return toItineraryList(localStorage.getListItinerary())
    }

    override fun getItinerary(itineraryID: String): Itinerary {
        return toItinerary(localStorage.getItinerary(itineraryID))
    }

    override fun deleteItinerary(itinerary: Itinerary): Int {
        return localStorage.removeItinerary(
            toItineraryRoomEntity(itinerary)
        )
    }

    override fun updateItinerary(itinerary: Itinerary): Int {
        return localStorage.changeItinerary(
            toItineraryRoomEntity(itinerary)
        )
    }
}