package com.example.myfirstapp.data.room.repository

import com.example.myfirstapp.domain.entity.Passenger
import com.example.myfirstapp.domain.repository.IRepositoryPassenger

class RoomRepositoryPassenger : IRepositoryPassenger {
    override fun addPassenger(passenger: Passenger): String {
        TODO("Not yet implemented")
    }

    override fun getPassenger(passengerId: String): Passenger {
        TODO("Not yet implemented")
    }

    override fun getPassengerListByItineraryId(itineraryId: String): List<Passenger> {
        TODO("Not yet implemented")
    }

    override fun updatePassenger(passenger: Passenger): Int {
        TODO("Not yet implemented")
    }

    override fun deletePassenger(passenger: Passenger): Int {
        TODO("Not yet implemented")
    }
}