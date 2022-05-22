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

    // DieselFuelSection
    @Query("SELECT * FROM dieselSection WHERE locomotiveDataID = :locomotiveDataID")
    fun getListDieselFuelSection(locomotiveDataID: String): MutableList<DieselFuelSectionRoomEntity>

    @Query("SELECT * FROM dieselSection WHERE sectionID = :sectionID")
    fun getDieselFuelSection(sectionID: String): DieselFuelSectionRoomEntity

    @Insert
    fun addDieselFuelSection(roomEntity: DieselFuelSectionRoomEntity): Long

    @Delete
    fun removeDieselFuelSection(roomEntity: DieselFuelSectionRoomEntity): Int

    @Update
    fun changeDieselFuelSection(roomEntity: DieselFuelSectionRoomEntity): Int

    @Query("UPDATE dieselSection SET accepted = :accepted WHERE sectionId = :sectionID")
    fun updateAcceptedDieselFuelSection(
        sectionID: String,
        accepted: Int?
    ): Int

    @Query("UPDATE dieselSection SET delivery = :delivery WHERE sectionId = :sectionID")
    fun updateDeliveryDieselFuelSection(
        sectionID: String,
        delivery: Int?
    ): Int


    @Query("UPDATE dieselSection SET consumption = :consumption WHERE sectionId = :sectionID")
    fun updateConsumptionDieselFuelSection(
        sectionID: String,
        consumption: Int?
    ): Int

    // ElectricSection
    @Query("SELECT * FROM electricSection WHERE locomotiveDataID = :locomotiveDataID")
    fun getListElectricSection(locomotiveDataID: String): MutableList<ElectricSectionRoomEntity>

    @Query("SELECT * FROM electricSection WHERE sectionID = :sectionID")
    fun getElectricSection(sectionID: String): ElectricSectionRoomEntity

    @Insert
    fun addElectricSection(roomEntity: ElectricSectionRoomEntity): Long

    @Delete
    fun removeElectricSection(roomEntity: ElectricSectionRoomEntity): Int

    @Update
    fun changeElectricSection(roomEntity: ElectricSectionRoomEntity): Int

    @Query("UPDATE electricSection SET acceptanceEnergy = :accepted WHERE sectionId = :sectionID")
    fun updateAcceptedEnergyElectricSection(
        sectionID: String,
        accepted: Int?
    ): Int

    @Query("UPDATE electricSection SET deliveryEnergy = :delivery WHERE sectionId = :sectionID")
    fun updateDeliveryEnergyElectricSection(
        sectionID: String,
        delivery: Int?
    ): Int


    @Query("UPDATE electricSection SET consumptionEnergy = :consumption WHERE sectionId = :sectionID")
    fun updateConsumptionEnergyElectricSection(
        sectionID: String,
        consumption: Int?
    ): Int

    @Query("UPDATE electricSection SET acceptanceRecovery = :accepted WHERE sectionId = :sectionID")
    fun updateAcceptedRecoveryElectricSection(
        sectionID: String,
        accepted: Int?
    ): Int

    @Query("UPDATE electricSection SET deliveryRecovery = :delivery WHERE sectionId = :sectionID")
    fun updateDeliveryRecoveryElectricSection(
        sectionID: String,
        delivery: Int?
    ): Int


    @Query("UPDATE electricSection SET consumptionRecovery = :consumption WHERE sectionId = :sectionID")
    fun updateConsumptionRecoveryElectricSection(
        sectionID: String,
        consumption: Int?
    ): Int

}