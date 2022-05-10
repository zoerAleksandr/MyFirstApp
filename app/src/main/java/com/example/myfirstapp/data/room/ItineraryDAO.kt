package com.example.myfirstapp.data.room

import androidx.room.*
import com.example.myfirstapp.data.room.entity.*

@Dao
interface ItineraryDAO {
    // Itinerary
    @Query("SELECT * FROM itinerary")
    fun getListItinerary(): MutableList<ItineraryRoomEntity>

    @Query("SELECT * FROM itinerary WHERE itineraryID = :itineraryID")
    fun getItinerary(itineraryID: Long): ItineraryRoomEntity

    @Insert
    fun addItinerary(itineraryRoomEntity: ItineraryRoomEntity)

    @Delete
    fun removeItinerary(itineraryRoomEntity: ItineraryRoomEntity)

    @Update
    fun changeItinerary(itineraryRoomEntity: ItineraryRoomEntity)

    // LocomotiveData
    @Query("SELECT * FROM locomotive WHERE locomotive_data_id = :locomotiveDataID")
    fun getLocomotiveData(locomotiveDataID: Long): LocomotiveDataRoomEntity

    @Query("SELECT * FROM locomotive WHERE itineraryID = :itineraryID")
    fun getListLocomotiveData(itineraryID: Long): MutableList<LocomotiveDataRoomEntity>

    @Insert
    fun addLocomotiveData(locomotiveDataRoomEntity: LocomotiveDataRoomEntity)

    @Delete
    fun removeLocomotiveData(locomotiveDataRoomEntity: LocomotiveDataRoomEntity)

    @Update
    fun changeLocomotiveData(locomotiveDataRoomEntity: LocomotiveDataRoomEntity)

    // TrainData
    @Query("SELECT * FROM train WHERE trainDataID = :trainDataID")
    fun getTrainData(trainDataID: Long): TrainDataRoomEntity

    @Query("SELECT * FROM train WHERE itineraryID = :itineraryID")
    fun getListTrainData(itineraryID: Long): MutableList<TrainDataRoomEntity>

    @Insert
    fun addTrainData(trainDataRoomEntity: TrainDataRoomEntity)

    @Delete
    fun removeTrainData(trainDataRoomEntity: TrainDataRoomEntity)

    @Update
    fun changeTrainData(trainDataRoomEntity: TrainDataRoomEntity)

    // FollowingByPassenger
    @Query("SELECT * FROM passenger WHERE followingByPassengerID = :followingByPassengerID")
    fun getFollowingByPassenger(followingByPassengerID: Long): FollowingByPassengerRoomEntity

    @Query("SELECT * FROM passenger WHERE itineraryID = :itineraryID")
    fun getListFollowingByPassenger(itineraryID: Long): MutableList<FollowingByPassengerRoomEntity>

    @Insert
    fun addFollowingByPassenger(followingByPassengerRoomEntity: FollowingByPassengerRoomEntity)

    @Delete
    fun removeFollowingByPassenger(followingByPassengerRoomEntity: FollowingByPassengerRoomEntity)

    @Update
    fun changeFollowingByPassenger(followingByPassengerRoomEntity: FollowingByPassengerRoomEntity)

    // Station
    @Query("SELECT * FROM station WHERE trainDataID = :trainDataID")
    fun getListStation(trainDataID: Long): MutableList<StationRoomEntity>
}