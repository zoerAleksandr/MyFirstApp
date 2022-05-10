package com.example.myfirstapp.domain.repository

import com.example.myfirstapp.domain.entity.*

interface IRepository {
    fun getListItinerary(): MutableList<Itinerary>
    fun getItinerary(itineraryID: Long): Itinerary

    fun getLocomotiveData(locomotiveDataID: Long): LocomotiveData
    fun getListLocomotiveData(itineraryID: Long): MutableList<LocomotiveData>

    fun getTrainData(trainDataID: Long): TrainData
    fun getListTrainData(itineraryID: Long): MutableList<TrainData>

    fun getListStation(trainDataID: Long): MutableList<Station>

    fun getFollowingByPassenger(followingByPassengerID: Long): FollowingByPassenger
    fun getListFollowingByPassenger(itineraryID: Long): List<FollowingByPassenger>

    fun addItinerary(itinerary: Itinerary)
    fun addLocomotiveData(locomotiveData: LocomotiveData)
    fun addTrainData(trainData: TrainData)
    fun addFallowingByPassenger(followingByPassenger: FollowingByPassenger)

    fun removeItinerary(itinerary: Itinerary)
    fun removeLocomotiveData(locomotiveData: LocomotiveData)
    fun removeTrainData(trainData: TrainData)
    fun removeFallowingByPassenger(followingByPassenger: FollowingByPassenger)

    fun changeItinerary(itinerary: Itinerary)
    fun changeLocomotiveData(locomotiveData: LocomotiveData)
    fun changeTrainData(trainData: TrainData)
    fun changeFallowingByPassenger(followingByPassenger: FollowingByPassenger)
}