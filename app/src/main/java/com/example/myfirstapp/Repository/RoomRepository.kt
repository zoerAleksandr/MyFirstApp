package com.example.myfirstapp.Repository

import com.example.myfirstapp.data.Itinerary
import com.example.myfirstapp.room.*

class RoomRepository(
    private val localStorage: ItineraryDAO,
) : Repository {
    companion object {
        fun newInstance(localStorage: ItineraryDAO) = RoomRepository(localStorage)
    }

    override fun getListItinerary(): MutableList<Itinerary?> = EntityItinerary.toItineraryList(localStorage.getListItinerary())

    override fun getItinerary(itineraryID: Long): EntityItinerary =
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

    override fun addItinerary(itinerary: EntityItinerary) = localStorage.addItinerary(itinerary)

    override fun addLocomotiveData(locomotiveData: EntityLocomotiveData) =
        localStorage.addLocomotiveData(locomotiveData)

    override fun addTrainData(trainData: EntityTrainData) =
        localStorage.addTrainData(trainData)

    override fun addFallowingByPassenger(followingByPassenger: EntityFollowingByPassenger) =
        localStorage.addFallowingByPassenger(followingByPassenger)

    override fun removeItinerary(itinerary: EntityItinerary) =
        localStorage.removeItinerary(itinerary)

    override fun removeLocomotiveData(locomotiveData: EntityLocomotiveData) =
        localStorage.removeLocomotiveData(locomotiveData)

    override fun removeTrainData(trainData: EntityTrainData) =
        localStorage.removeTrainData(trainData)

    override fun removeFallowingByPassenger(followingByPassenger: EntityFollowingByPassenger) =
        localStorage.removeFallowingByPassenger(followingByPassenger)

    override fun changeItinerary(itinerary: EntityItinerary) =
        localStorage.changeItinerary(itinerary)

    override fun changeLocomotiveData(locomotiveData: EntityLocomotiveData) =
        localStorage.changeLocomotiveData(locomotiveData)

    override fun changeTrainData(trainData: EntityTrainData) =
        localStorage.changeTrainData(trainData)

    override fun changeFallowingByPassenger(followingByPassenger: EntityFollowingByPassenger) =
        localStorage.changeFallowingByPassenger(followingByPassenger)
}