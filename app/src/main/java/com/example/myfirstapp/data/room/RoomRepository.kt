package com.example.myfirstapp.data.room

import com.example.myfirstapp.domain.entity.*
import com.example.myfirstapp.domain.repository.IRepository
import com.example.myfirstapp.utils.convert_entity.*
import io.reactivex.rxjava3.core.Single
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RoomRepository : IRepository, KoinComponent {
    private val localStorage: ItineraryDAO by inject()

    override fun getListItinerary(): Single<MutableList<Itinerary>> {
        return toItineraryList(localStorage.getListItinerary())
    }

    override fun getItinerary(itineraryID: String): Single<Itinerary> =
        Single.just(
            toItinerary(localStorage.getItinerary(itineraryID))
        )

    override fun getLocomotiveData(locomotiveDataID: String): Single<LocomotiveData> =
        Single.just(
            toLocomotiveData(localStorage.getLocomotiveData(locomotiveDataID))
        )

    override fun getListLocomotiveData(itineraryID: String): Single<MutableList<LocomotiveData>> =
        Single.just(
            toLocomotiveDataList(localStorage.getListLocomotiveData(itineraryID))
        )

    override fun getTrainData(trainDataID: String): Single<TrainData> =
        Single.just(
            toTrainData(localStorage.getTrainData(trainDataID))
        )

    override fun getListTrainData(itineraryID: String): Single<MutableList<TrainData>> =
        Single.just(
            toTrainDataList(localStorage.getListTrainData(itineraryID))
        )

    override fun getListStation(trainDataID: String): Single<MutableList<Station>> =
        Single.just(
            toStationList(localStorage.getListStation(trainDataID))
        )

    override fun getFollowingByPassenger(followingByPassengerID: String): Single<FollowingByPassenger> =
        Single.just(
            toFollowingByPassenger(
                localStorage.getFollowingByPassenger(followingByPassengerID)
            )
        )

    override fun getListFollowingByPassenger(itineraryID: String): Single<List<FollowingByPassenger>> =
        Single.just(
            toFollowingByPassengerList(localStorage.getListFollowingByPassenger(itineraryID))
        )

    override fun getDieselFuelSectionList(locomotiveDataID: String): Single<List<DieselFuelSection>> {
        return Single.just(
            toDieselFuelSectionList(localStorage.getListDieselFuelSection(locomotiveDataID))
        )
    }

    override fun getDieselFuelSection(sectionID: String): Single<DieselFuelSection> {
        return Single.just(
            toDieselFuelSection(
                localStorage.getDieselFuelSection(sectionID)
            )
        )
    }

    override fun getElectricSectionList(locomotiveDataID: String): Single<List<ElectricSection>> {
        return Single.just(
            toElectricSectionList(
                localStorage.getListElectricSection(locomotiveDataID)
            )
        )
    }

    override fun getElectricSection(sectionID: String): Single<ElectricSection> {
        return Single.just(
            toElectricSection(
                localStorage.getElectricSection(sectionID)
            )
        )
    }

    override fun addItinerary(itinerary: Itinerary): Single<Long> {
        return Single.just(
            localStorage.addItinerary(
                toItineraryRoomEntity(itinerary)
            )
        )
    }

    override fun addLocomotiveData(locomotiveData: LocomotiveData): Single<Long> {
        return Single.just(
            localStorage.addLocomotiveData(
                toLocomotiveDataRoomEntity(locomotiveData)
            )
        )
    }

    override fun addTrainData(trainData: TrainData): Single<Long> {
        return Single.just(
            localStorage.addTrainData(
                toTrainDataRoomEntity(trainData)
            )
        )
    }

    override fun addFallowingByPassenger(followingByPassenger: FollowingByPassenger): Single<Long> {
        return Single.just(
            localStorage.addFollowingByPassenger(
                toFollowingByPassengerRoomEntity(followingByPassenger)
            )
        )
    }

    override fun addDieselFuelSection(dieselFuelSection: DieselFuelSection): Single<Long> {
        return Single.just(
            localStorage.addDieselFuelSection(
                toDieselFuelSectionRoomEntity(dieselFuelSection)
            )
        )
    }

    override fun addElectricSection(electricSection: ElectricSection): Single<Long> {
        return Single.just(
            localStorage.addElectricSection(
                toElectricSectionRoomEntity(electricSection)
            )
        )
    }

    override fun removeItinerary(itinerary: Itinerary): Single<Int> {
        return Single.just(
            localStorage.removeItinerary(
                toItineraryRoomEntity(itinerary)
            )
        )
    }

    override fun removeLocomotiveData(locomotiveData: LocomotiveData): Single<Int> {
        return Single.just(
            localStorage.removeLocomotiveData(
                toLocomotiveDataRoomEntity(locomotiveData)
            )
        )
    }

    override fun removeTrainData(trainData: TrainData): Single<Int> {
        return Single.just(
            localStorage.removeTrainData(
                toTrainDataRoomEntity(trainData)
            )
        )
    }

    override fun removeFallowingByPassenger(followingByPassenger: FollowingByPassenger): Single<Int> {
        return Single.just(
            localStorage.removeFollowingByPassenger(
                toFollowingByPassengerRoomEntity(followingByPassenger)
            )
        )
    }

    override fun removeDieselFuelSection(dieselFuelSection: DieselFuelSection): Single<Int> {
        return Single.just(
            localStorage.removeDieselFuelSection(
                toDieselFuelSectionRoomEntity(dieselFuelSection)
            )
        )
    }

    override fun removeElectricSection(electricSection: ElectricSection): Single<Int> {
        return Single.just(
            localStorage.removeElectricSection(
                toElectricSectionRoomEntity(electricSection)
            )
        )
    }

    override fun changeItinerary(itinerary: Itinerary): Single<Int> {
        return Single.just(
            localStorage.changeItinerary(
                toItineraryRoomEntity(itinerary)
            )
        )
    }

    override fun changeLocomotiveData(locomotiveData: LocomotiveData): Single<Int> {
        return Single.just(
            localStorage.changeLocomotiveData(
                toLocomotiveDataRoomEntity(locomotiveData)
            )
        )
    }

    override fun changeTrainData(trainData: TrainData): Single<Int> {
        return Single.just(
            localStorage.changeTrainData(
                toTrainDataRoomEntity(trainData)
            )
        )
    }

    override fun changeFallowingByPassenger(followingByPassenger: FollowingByPassenger): Single<Int> {
        return Single.just(
            localStorage.changeFollowingByPassenger(
                toFollowingByPassengerRoomEntity(followingByPassenger)
            )
        )
    }

    override fun changeDieselFuelSection(dieselFuelSection: DieselFuelSection): Single<Int> {
        return Single.just(
            localStorage.changeDieselFuelSection(
                toDieselFuelSectionRoomEntity(dieselFuelSection)
            )
        )
    }

    override fun changeElectricSection(electricSection: ElectricSection): Single<Int> {
        return Single.just(
            localStorage.changeElectricSection(
                toElectricSectionRoomEntity(electricSection)
            )
        )
    }

    override fun updateAcceptedDieselFuelSection(
        sectionID: String,
        accepted: Int?
    ): Single<Int> {
        return Single.just(
            localStorage.updateAcceptedDieselFuelSection(
                sectionID, accepted
            )
        )
    }

    override fun updateDeliveryDieselFuelSection(
        sectionID: String,
        delivery: Int?
    ): Single<Int> {
        return Single.just(
            localStorage.updateDeliveryDieselFuelSection(
                sectionID, delivery
            )
        )
    }
    override fun updateConsumptionDieselFuelSection(
        sectionID: String,
        consumption: Int?
    ): Single<Int> {
        return Single.just(
            localStorage.updateConsumptionDieselFuelSection(
                sectionID, consumption
            )
        )
    }

    override fun updateAcceptedEnergyElectricSection(
        sectionID: String,
        accepted: Int?
    ): Single<Int> {
        return Single.just(
            localStorage.updateAcceptedEnergyElectricSection(
                sectionID, accepted
            )
        )
    }

    override fun updateDeliveryEnergyElectricSection(
        sectionID: String,
        delivery: Int?
    ): Single<Int> {
        return Single.just(
            localStorage.updateDeliveryEnergyElectricSection(
                sectionID, delivery
            )
        )
    }

    override fun updateConsumptionEnergyElectricSection(
        sectionID: String,
        consumption: Int?
    ): Single<Int> {
        return Single.just(
            localStorage.updateConsumptionEnergyElectricSection(
                sectionID, consumption
            )
        )
    }

    override fun updateAcceptedRecoveryElectricSection(
        sectionID: String,
        accepted: Int?
    ): Single<Int> {
        return Single.just(
            localStorage.updateAcceptedRecoveryElectricSection(
                sectionID, accepted
            )
        )
    }

    override fun updateDeliveryRecoveryElectricSection(
        sectionID: String,
        delivery: Int?
    ): Single<Int> {
        return Single.just(
            localStorage.updateDeliveryRecoveryElectricSection(
                sectionID, delivery
            )
        )
    }

    override fun updateConsumptionRecoveryElectricSection(
        sectionID: String,
        consumption: Int?
    ): Single<Int> {
        return Single.just(
            localStorage.updateConsumptionRecoveryElectricSection(
                sectionID, consumption
            )
        )
    }

    override fun updateNumberLoco(locomotiveDataID: String, number: String?): Single<Int> {
        return Single.just(
            localStorage.updateNumberLoco(locomotiveDataID, number)
        )
    }

    override fun updateSeriesLoco(locomotiveDataID: String, series: String?): Single<Int> {
        return Single.just(
            localStorage.updateSeriesLoco(locomotiveDataID, series)
        )
    }

    override fun updateTypeOfTraction(
        locomotiveDataID: String,
        typeOfTraction: TypeOfTraction
    ): Single<Int> {
        return Single.just(
            localStorage.updateTypeOfTraction(locomotiveDataID, typeOfTraction)
        )
    }

    override fun updateCountSection(
        locomotiveDataID: String,
        countSections: CountSections
    ): Single<Int> {
        return Single.just(
            localStorage.updateCountSection(locomotiveDataID, countSections)
        )
    }
}