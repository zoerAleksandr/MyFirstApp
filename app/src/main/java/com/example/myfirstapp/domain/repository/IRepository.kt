package com.example.myfirstapp.domain.repository

import com.example.myfirstapp.data.room.entity.*
import com.example.myfirstapp.domain.entity.*

interface IRepository {
    fun getListItinerary(): MutableList<Itinerary>
    fun getItinerary(itineraryID: Long): Itinerary

    fun getLocomotiveData(locomotiveDataID: Long): LocomotiveData
    fun getListLocomotiveData(itineraryID: Long): List<LocomotiveData>

    fun getTrainData(trainDataID: Long): TrainData
    fun getListTrainData(itineraryID: Long): List<TrainData>

    fun getListStation(trainDataID: Long): List<Station>

    fun getFallowingByPassenger(followingByPassengerID: Long): FollowingByPassenger
    fun getListFollowingByPassenger(itineraryID: Long): List<FollowingByPassenger>

    fun addItinerary(itineraryRoomEntity: Itinerary)
    fun addLocomotiveData(locomotiveData: LocomotiveData)
    fun addTrainData(trainData: TrainData)
    fun addFallowingByPassenger(followingByPassenger: FollowingByPassenger)

    fun removeItinerary(itineraryRoomEntity: Itinerary)
    fun removeLocomotiveData(locomotiveData: LocomotiveData)
    fun removeTrainData(trainData: EntityTrainData)
    fun removeFallowingByPassenger(followingByPassenger: FollowingByPassenger)

    fun changeItinerary(itineraryRoomEntity: Itinerary)
    fun changeLocomotiveData(locomotiveData: LocomotiveData)
    fun changeTrainData(trainData: TrainData)
    fun changeFallowingByPassenger(followingByPassenger: FollowingByPassenger)
}