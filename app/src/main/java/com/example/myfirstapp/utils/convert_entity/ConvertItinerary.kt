package com.example.myfirstapp.utils.convert_entity

import com.example.myfirstapp.data.room.entity.ItineraryRoomEntity
import com.example.myfirstapp.domain.entity.Itinerary

fun toItineraryList(
    roomEntityList: MutableList<ItineraryRoomEntity>
): MutableList<Itinerary> {
    val list = mutableListOf<Itinerary>()
    for (item in roomEntityList) {
        list.add(toItinerary(item))
    }
    return list
}

fun toItinerary(roomEntity: ItineraryRoomEntity): Itinerary {
    return Itinerary(
        roomEntity.id,
        roomEntity.number,
        roomEntity.appearanceAtWork,
        roomEntity.endOfWork,
        roomEntity.restAtThePointOfTurnover,
        roomEntity.notes
    )
}

fun toItineraryRoomEntity(itinerary: Itinerary): ItineraryRoomEntity {
    return ItineraryRoomEntity(
        itinerary.itineraryID,
        itinerary.number,
        itinerary.appearanceAtWork,
        itinerary.endOfWork,
        itinerary.restAtThePointOfTurnover,
        itinerary.notes
    )
}