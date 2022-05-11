package com.example.myfirstapp.utils.convert_entity

import com.example.myfirstapp.data.room.entity.ItineraryRoomEntity
import com.example.myfirstapp.domain.entity.Itinerary
import io.reactivex.rxjava3.core.Single

fun toItineraryList(
    roomEntityList: MutableList<ItineraryRoomEntity>
): Single<MutableList<Itinerary>> {
    val list = mutableListOf<Itinerary>()
    for (item in roomEntityList) {
        list.add(toItinerary(item))
    }
    return Single.just(list)
}

fun toItinerary(roomEntity: ItineraryRoomEntity): Itinerary {
    return Itinerary(
        roomEntity.itineraryID,
        roomEntity.number,
        roomEntity.appearanceAtWork,
        roomEntity.endOfWork,
        roomEntity.restAtThePointOfTurnover,
        roomEntity.notes,
        roomEntity.followingByPassengerList,
        roomEntity.locomotiveDataList,
        roomEntity.trainDataList
    )
}

fun toItineraryRoomEntity(itinerary: Itinerary): ItineraryRoomEntity {
    return ItineraryRoomEntity(
        itinerary.itineraryID,
        itinerary.number,
        itinerary.appearanceAtWork,
        itinerary.endOfWork,
        itinerary.restAtThePointOfTurnover,
        itinerary.notes,
        itinerary.followingByPassengerList,
        itinerary.locomotiveDataList,
        itinerary.trainDataList
    )
}