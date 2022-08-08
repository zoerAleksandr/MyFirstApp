package com.example.myfirstapp.data.room.repository

import com.example.myfirstapp.data.room.ItineraryDAO
import com.example.myfirstapp.domain.entity.Passenger
import com.example.myfirstapp.domain.repository.IRepositoryPassenger
import com.example.myfirstapp.utils.convert_entity.toPassengerList
import com.example.myfirstapp.utils.convert_entity.toPassengerRoomEntity
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RoomRepositoryPassenger : IRepositoryPassenger, KoinComponent {
    private val localStorage: ItineraryDAO by inject()

    override fun addPassenger(passenger: Passenger): Long {
        return localStorage.addFollowingByPassenger(
            toPassengerRoomEntity(passenger)
        )
    }

    override fun getPassenger(passengerId: String): Passenger {
        TODO("Not yet implemented")
    }

    override fun getPassengerListByItineraryIdAsync(itineraryId: String): MutableList<Passenger> {
        return toPassengerList(
            localStorage.getListFollowingByPassenger(itineraryId)
        )
    }

    override fun updatePassenger(passenger: Passenger): Int {
        TODO("Not yet implemented")
    }

    override fun deletePassenger(passenger: Passenger): Int {
        TODO("Not yet implemented")
    }
}