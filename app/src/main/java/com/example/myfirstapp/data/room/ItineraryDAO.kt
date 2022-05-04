package com.example.myfirstapp.data.room

import androidx.room.*

@Dao
interface ItineraryDAO {
    // Itinerary
    @Query("SELECT * FROM itinerary")
    fun getListItinerary(): MutableList<EntityItinerary>

    @Query("SELECT * FROM itinerary WHERE itineraryID = :itineraryID")
    fun getItinerary(itineraryID: Long): EntityItinerary

    @Insert
    fun addItinerary(itinerary: EntityItinerary)

    @Delete
    fun removeItinerary(itinerary: EntityItinerary)

    @Update
    fun changeItinerary(itinerary: EntityItinerary)

    // LocomotiveData
    @Query("SELECT * FROM locomotive WHERE locomotive_data_id = :locomotiveDataID")
    fun getLocomotiveData(locomotiveDataID: Long): EntityLocomotiveData

    @Query("SELECT * FROM locomotive WHERE itineraryID = :itineraryID")
    fun getListLocomotiveData(itineraryID: Long): List<EntityLocomotiveData>

    @Insert
    fun addLocomotiveData(locomotiveData: EntityLocomotiveData)

    @Delete
    fun removeLocomotiveData(locomotiveData: EntityLocomotiveData)

    @Update
    fun changeLocomotiveData(locomotiveData: EntityLocomotiveData)

    // TrainData
    @Query("SELECT * FROM train WHERE trainDataID = :trainDataID")
    fun getTrainData(trainDataID: Long): EntityTrainData

    @Query("SELECT * FROM train WHERE itineraryID = :itineraryID")
    fun getListTrainData(itineraryID: Long): List<EntityTrainData>

    @Insert
    fun addTrainData(trainData: EntityTrainData)

    @Delete
    fun removeTrainData(trainData: EntityTrainData)

    @Update
    fun changeTrainData(trainData: EntityTrainData)

    // FollowingByPassenger
    @Query("SELECT * FROM passenger WHERE followingByPassengerID = :followingByPassengerID")
    fun getFollowingByPassenger(followingByPassengerID: Long): EntityFollowingByPassenger

    @Query("SELECT * FROM passenger WHERE itineraryID = :itineraryID")
    fun getListFollowingByPassenger(itineraryID: Long): List<EntityFollowingByPassenger>

    @Insert
    fun addFallowingByPassenger(followingByPassenger: EntityFollowingByPassenger)

    @Delete
    fun removeFallowingByPassenger(followingByPassenger: EntityFollowingByPassenger)

    @Update
    fun changeFallowingByPassenger(followingByPassenger: EntityFollowingByPassenger)

    // Station
    @Query("SELECT * FROM station WHERE trainDataID = :trainDataID")
    fun getListStation(trainDataID: Long): List<EntityStation>
}