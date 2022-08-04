package com.example.myfirstapp.domain.repository

import com.example.myfirstapp.domain.entity.Passenger

interface IRepositoryPassenger {
    fun addPassenger(passenger: Passenger): String
    fun getPassenger(passengerId: String): Passenger
    fun getPassengerListByItineraryId(itineraryId: String): List<Passenger>
    fun updatePassenger(passenger: Passenger): Int
    fun deletePassenger(passenger: Passenger): Int
}