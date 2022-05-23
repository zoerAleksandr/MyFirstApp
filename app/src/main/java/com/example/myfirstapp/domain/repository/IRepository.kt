package com.example.myfirstapp.domain.repository

import com.example.myfirstapp.domain.entity.*
import io.reactivex.rxjava3.core.Single
import java.util.*

interface IRepository {
    fun getListItinerary(): Single<MutableList<Itinerary>>
    fun getItinerary(itineraryID: String): Single<Itinerary>

    fun getLocomotiveData(locomotiveDataID: String): Single<LocomotiveData>
    fun getListLocomotiveData(itineraryID: String): Single<MutableList<LocomotiveData>>

    fun getTrainData(trainDataID: String): Single<TrainData>
    fun getListTrainData(itineraryID: String): Single<MutableList<TrainData>>

    fun getListStation(trainDataID: String): Single<MutableList<Station>>

    fun getFollowingByPassenger(followingByPassengerID: String): Single<FollowingByPassenger>
    fun getListFollowingByPassenger(itineraryID: String): Single<List<FollowingByPassenger>>

    fun getDieselFuelSectionList(locomotiveDataID: String): Single<List<DieselFuelSection>>
    fun getDieselFuelSection(sectionID: String): Single<DieselFuelSection>

    fun getElectricSectionList(locomotiveDataID: String): Single<List<ElectricSection>>
    fun getElectricSection(sectionID: String): Single<ElectricSection>

    fun addItinerary(itinerary: Itinerary): Single<Long>
    fun addLocomotiveData(locomotiveData: LocomotiveData): Single<Long>
    fun addTrainData(trainData: TrainData): Single<Long>
    fun addFallowingByPassenger(followingByPassenger: FollowingByPassenger): Single<Long>
    fun addDieselFuelSection(dieselFuelSection: DieselFuelSection): Single<Long>
    fun addElectricSection(electricSection: ElectricSection): Single<Long>

    fun removeItinerary(itinerary: Itinerary): Single<Int>
    fun removeLocomotiveData(locomotiveData: LocomotiveData): Single<Int>
    fun removeTrainData(trainData: TrainData): Single<Int>
    fun removeFallowingByPassenger(followingByPassenger: FollowingByPassenger): Single<Int>
    fun removeDieselFuelSection(dieselFuelSection: DieselFuelSection): Single<Int>
    fun removeElectricSection(electricSection: ElectricSection): Single<Int>

    fun changeItinerary(itinerary: Itinerary): Single<Int>
    fun changeLocomotiveData(locomotiveData: LocomotiveData): Single<Int>
    fun changeTrainData(trainData: TrainData): Single<Int>
    fun changeFallowingByPassenger(followingByPassenger: FollowingByPassenger): Single<Int>
    fun changeDieselFuelSection(dieselFuelSection: DieselFuelSection): Single<Int>
    fun changeElectricSection(electricSection: ElectricSection): Single<Int>

    fun updateAcceptedDieselFuelSection(sectionID: String, accepted: Int?): Single<Int>
    fun updateDeliveryDieselFuelSection(sectionID: String, delivery: Int?): Single<Int>
    fun updateConsumptionDieselFuelSection(sectionID: String, consumption: Int?): Single<Int>

    fun updateAcceptedEnergyElectricSection(sectionID: String, accepted: Int?): Single<Int>
    fun updateDeliveryEnergyElectricSection(sectionID: String, delivery: Int?): Single<Int>
    fun updateConsumptionEnergyElectricSection(sectionID: String, consumption: Int?): Single<Int>

    fun updateAcceptedRecoveryElectricSection(sectionID: String, accepted: Int?): Single<Int>
    fun updateDeliveryRecoveryElectricSection(sectionID: String, delivery: Int?): Single<Int>
    fun updateConsumptionRecoveryElectricSection(sectionID: String, consumption: Int?): Single<Int>

    fun updateNumberLoco(locomotiveDataID: String, number: String?): Single<Int>
    fun updateSeriesLoco(locomotiveDataID: String, series: String?): Single<Int>

    fun updateTypeOfTraction(locomotiveDataID: String, typeOfTraction: TypeOfTraction): Single<Int>
    fun updateCountSection(locomotiveDataID: String, countSections: CountSections): Single<Int>

    fun updateStartAcceptance(locomotiveDataID: String, calendar: Calendar?): Single<Int>
    fun updateEndAcceptance(locomotiveDataID: String, calendar: Calendar?): Single<Int>
    fun updateStartDelivery(locomotiveDataID: String, calendar: Calendar?): Single<Int>
    fun updateEndDelivery(locomotiveDataID: String, calendar: Calendar?): Single<Int>

    fun updateBreakShoes(locomotiveDataID: String, count: Int?): Single<Int>
    fun updateExtinguishers(locomotiveDataID: String, count: Int?): Single<Int>
}