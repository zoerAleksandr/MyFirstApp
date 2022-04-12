package com.example.myfirstapp.Repository

import com.example.myfirstapp.data.Itinerary
import com.example.myfirstapp.room.*

interface Repository {
//    fun getDataFromLocal(): MutableList<Itinerary>
//    fun getDataFromServer(): List<Itinerary>
//    fun addData(itinerary: Itinerary)
//    fun remove(position: Int)
//    fun getSize(): Int
//    fun getItinerary(position: Int): Itinerary

    fun getListItinerary(): MutableList<Itinerary?>
    fun getItinerary(itineraryID: Long): EntityItinerary
    fun getLocomotiveData(locomotiveDataID: Long): EntityLocomotiveData
    fun getListLocomotiveData(itineraryID: Long): List<EntityLocomotiveData>
    fun getTrainData(trainDataID: Long): EntityTrainData
    fun getListTrainData(itineraryID: Long): List<EntityTrainData>
    fun getListStation(trainDataID: Long): List<EntityStation>
    fun getFallowingByPassenger(followingByPassengerID: Long): EntityFollowingByPassenger
    fun getListFollowingByPassenger(itineraryID: Long): List<EntityFollowingByPassenger>

    fun addItinerary(itinerary: EntityItinerary)
    fun addLocomotiveData(locomotiveData: EntityLocomotiveData)
    fun addTrainData(trainData: EntityTrainData)
    fun addFallowingByPassenger(followingByPassenger: EntityFollowingByPassenger)

    fun removeItinerary(itinerary: EntityItinerary)
    fun removeLocomotiveData(locomotiveData: EntityLocomotiveData)
    fun removeTrainData(trainData: EntityTrainData)
    fun removeFallowingByPassenger(followingByPassenger: EntityFollowingByPassenger)

    fun changeItinerary(itinerary: EntityItinerary)
    fun changeLocomotiveData(locomotiveData: EntityLocomotiveData)
    fun changeTrainData(trainData: EntityTrainData)
    fun changeFallowingByPassenger(followingByPassenger: EntityFollowingByPassenger)


}