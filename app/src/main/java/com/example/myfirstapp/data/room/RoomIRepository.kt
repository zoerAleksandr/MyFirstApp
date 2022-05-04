package com.example.myfirstapp.data.room

import com.example.myfirstapp.data.room.entity.*
import com.example.myfirstapp.domain.repository.IRepository
import com.example.myfirstapp.domain.entity.Itinerary

class RoomIRepository(
    private val localStorage: ItineraryDAO,
) : IRepository {
    companion object {
        fun newInstance(localStorage: ItineraryDAO) = RoomIRepository(localStorage)
    }

    override fun getListItinerary(): MutableList<Itinerary?> =
        ItineraryRoomEntity.toItineraryList(localStorage.getListItinerary())

    override fun getItinerary(itineraryID: Long): ItineraryRoomEntity =
        localStorage.getItinerary(itineraryID)

    override fun getLocomotiveData(locomotiveDataID: Long): EntityLocomotiveData =
        localStorage.getLocomotiveData(locomotiveDataID)

    override fun getListLocomotiveData(itineraryID: Long): List<EntityLocomotiveData> =
        localStorage.getListLocomotiveData(itineraryID)

    override fun getTrainData(trainDataID: Long): EntityTrainData =
        localStorage.getTrainData(trainDataID)

    override fun getListTrainData(itineraryID: Long): List<EntityTrainData> =
        localStorage.getListTrainData(itineraryID)

    override fun getListStation(trainDataID: Long): List<EntityStation> =
        localStorage.getListStation(trainDataID)

    override fun getFallowingByPassenger(followingByPassengerID: Long): EntityFollowingByPassenger =
        localStorage.getFollowingByPassenger(followingByPassengerID)

    override fun getListFollowingByPassenger(itineraryID: Long): List<EntityFollowingByPassenger> =
        localStorage.getListFollowingByPassenger(itineraryID)

    override fun addItinerary(itineraryRoomEntity: ItineraryRoomEntity) = localStorage.addItinerary(itineraryRoomEntity)

    override fun addLocomotiveData(locomotiveData: EntityLocomotiveData) =
        localStorage.addLocomotiveData(locomotiveData)

    override fun addTrainData(trainData: EntityTrainData) =
        localStorage.addTrainData(trainData)

    override fun addFallowingByPassenger(followingByPassenger: EntityFollowingByPassenger) =
        localStorage.addFallowingByPassenger(followingByPassenger)

    override fun removeItinerary(itineraryRoomEntity: ItineraryRoomEntity) =
        localStorage.removeItinerary(itineraryRoomEntity)

    override fun removeLocomotiveData(locomotiveData: EntityLocomotiveData) =
        localStorage.removeLocomotiveData(locomotiveData)

    override fun removeTrainData(trainData: EntityTrainData) =
        localStorage.removeTrainData(trainData)

    override fun removeFallowingByPassenger(followingByPassenger: EntityFollowingByPassenger) =
        localStorage.removeFallowingByPassenger(followingByPassenger)

    override fun changeItinerary(itineraryRoomEntity: ItineraryRoomEntity) =
        localStorage.changeItinerary(itineraryRoomEntity)

    override fun changeLocomotiveData(locomotiveData: EntityLocomotiveData) =
        localStorage.changeLocomotiveData(locomotiveData)

    override fun changeTrainData(trainData: EntityTrainData) =
        localStorage.changeTrainData(trainData)

    override fun changeFallowingByPassenger(followingByPassenger: EntityFollowingByPassenger) =
        localStorage.changeFallowingByPassenger(followingByPassenger)
}