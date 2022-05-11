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
}