package com.example.myfirstapp.data.room

import androidx.room.*
import com.example.myfirstapp.data.room.entity.*

@Dao
interface ItineraryDAO {
    // Itinerary
    @Query("SELECT * FROM itinerary")
    fun getListItinerary(): MutableList<ItineraryRoomEntity>

    @Query("SELECT * FROM itinerary WHERE itineraryID = :itineraryID")
    fun getItinerary(itineraryID: String): ItineraryRoomEntity

    @Insert
    fun addItinerary(roomEntity: ItineraryRoomEntity): Long

    @Delete
    fun removeItinerary(roomEntity: ItineraryRoomEntity): Int

    @Update
    fun changeItinerary(roomEntity: ItineraryRoomEntity): Int

    // LocomotiveData
    @Query("SELECT * FROM locomotive WHERE locomotive_data_id = :locomotiveDataID")
    fun getLocomotiveData(locomotiveDataID: String): LocomotiveDataRoomEntity

    @Query("SELECT * FROM locomotive WHERE itineraryID = :itineraryID")
    fun getListLocomotiveData(itineraryID: String): MutableList<LocomotiveDataRoomEntity>

    @Insert
    fun addLocomotiveData(roomEntity: LocomotiveDataRoomEntity): Long

    @Delete
    fun removeLocomotiveData(roomEntity: LocomotiveDataRoomEntity): Int

    @Update
    fun changeLocomotiveData(roomEntity: LocomotiveDataRoomEntity): Int

    // TrainData
    @Query("SELECT * FROM train WHERE trainDataID = :trainDataID")
    fun getTrainData(trainDataID: String): TrainDataRoomEntity

    @Query("SELECT * FROM train WHERE itineraryID = :itineraryID")
    fun getListTrainData(itineraryID: String): MutableList<TrainDataRoomEntity>

    @Insert
    fun addTrainData(roomEntity: TrainDataRoomEntity): Long

    @Delete
    fun removeTrainData(roomEntity: TrainDataRoomEntity): Int

    @Update
    fun changeTrainData(roomEntity: TrainDataRoomEntity): Int

    // FollowingByPassenger
    @Query("SELECT * FROM passenger WHERE followingByPassengerID = :followingByPassengerID")
    fun getFollowingByPassenger(followingByPassengerID: String): FollowingByPassengerRoomEntity

    @Query("SELECT * FROM passenger WHERE itineraryID = :itineraryID")
    fun getListFollowingByPassenger(itineraryID: String): MutableList<FollowingByPassengerRoomEntity>

    @Insert
    fun addFollowingByPassenger(roomEntity: FollowingByPassengerRoomEntity): Long

    @Delete
    fun removeFollowingByPassenger(roomEntity: FollowingByPassengerRoomEntity): Int

    @Update
    fun changeFollowingByPassenger(roomEntity: FollowingByPassengerRoomEntity): Int

    // Station
    @Query("SELECT * FROM station WHERE trainDataID = :trainDataID")
    fun getListStation(trainDataID: String): MutableList<StationRoomEntity>
}