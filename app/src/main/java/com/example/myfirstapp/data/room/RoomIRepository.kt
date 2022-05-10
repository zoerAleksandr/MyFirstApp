package com.example.myfirstapp.data.room

import com.example.myfirstapp.domain.entity.*
import com.example.myfirstapp.domain.repository.IRepository
import com.example.myfirstapp.utils.convert_entity.*

class RoomIRepository(
    private val localStorage: ItineraryDAO,
) : IRepository {
    companion object {
        fun newInstance(localStorage: ItineraryDAO) = RoomIRepository(localStorage)
    }

    override fun getListItinerary(): MutableList<Itinerary> =
        toItineraryList(localStorage.getListItinerary())

    override fun getItinerary(itineraryID: Long): Itinerary =
        toItinerary(localStorage.getItinerary(itineraryID))

    override fun getLocomotiveData(locomotiveDataID: Long): LocomotiveData =
        toLocomotiveData(localStorage.getLocomotiveData(locomotiveDataID))

    override fun getListLocomotiveData(itineraryID: Long): MutableList<LocomotiveData> =
        toLocomotiveDataList(localStorage.getListLocomotiveData(itineraryID))

    override fun getTrainData(trainDataID: Long): TrainData =
        toTrainData(localStorage.getTrainData(trainDataID))

    override fun getListTrainData(itineraryID: Long): MutableList<TrainData> =
        toTrainDataList(localStorage.getListTrainData(itineraryID))

    override fun getListStation(trainDataID: Long): MutableList<Station> =
        toStationList(localStorage.getListStation(trainDataID))

    override fun getFollowingByPassenger(followingByPassengerID: Long): FollowingByPassenger =
        toFollowingByPassenger(localStorage.getFollowingByPassenger(followingByPassengerID))

    override fun getListFollowingByPassenger(itineraryID: Long): List<FollowingByPassenger> =
        toFollowingByPassengerList(localStorage.getListFollowingByPassenger(itineraryID))

    override fun addItinerary(itinerary: Itinerary) {
        localStorage.addItinerary(
            toItineraryRoomEntity(itinerary)
        )
    }

    override fun addLocomotiveData(locomotiveData: LocomotiveData) {
        localStorage.addLocomotiveData(
            toLocomotiveDataRoomEntity(locomotiveData)
        )
    }

    override fun addTrainData(trainData: TrainData) {
        localStorage.addTrainData(
            toTrainDataRoomEntity(trainData)
        )
    }

    override fun addFallowingByPassenger(followingByPassenger: FollowingByPassenger) {
        localStorage.addFollowingByPassenger(
            toFollowingByPassengerRoomEntity(followingByPassenger)
        )
    }

    override fun removeItinerary(itinerary: Itinerary) {
        localStorage.removeItinerary(
            toItineraryRoomEntity(itinerary)
        )
    }

    override fun removeLocomotiveData(locomotiveData: LocomotiveData) {
        localStorage.removeLocomotiveData(
            toLocomotiveDataRoomEntity(locomotiveData)
        )
    }

    override fun removeTrainData(trainData: TrainData) {
        localStorage.removeTrainData(
            toTrainDataRoomEntity(trainData)
        )
    }

    override fun removeFallowingByPassenger(followingByPassenger: FollowingByPassenger) {
        localStorage.removeFollowingByPassenger(
            toFollowingByPassengerRoomEntity(followingByPassenger)
        )
    }

    override fun changeItinerary(itinerary: Itinerary) {
        localStorage.changeItinerary(
            toItineraryRoomEntity(itinerary)
        )
    }

    override fun changeLocomotiveData(locomotiveData: LocomotiveData) {
        localStorage.changeLocomotiveData(
            toLocomotiveDataRoomEntity(locomotiveData)
        )
    }

    override fun changeTrainData(trainData: TrainData) {
        localStorage.changeTrainData(
            toTrainDataRoomEntity(trainData)
        )
    }

    override fun changeFallowingByPassenger(followingByPassenger: FollowingByPassenger) {
        localStorage.changeFollowingByPassenger(
            toFollowingByPassengerRoomEntity(followingByPassenger)
        )
    }
}