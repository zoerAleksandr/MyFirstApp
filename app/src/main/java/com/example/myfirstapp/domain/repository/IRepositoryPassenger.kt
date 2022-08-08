package com.example.myfirstapp.domain.repository

import com.example.myfirstapp.domain.entity.Passenger

interface IRepositoryPassenger {
    fun addPassenger(passenger: Passenger): Long
    fun getPassenger(passengerId: String): Passenger
    fun getPassengerListByItineraryIdAsync(itineraryId: String): MutableList<Passenger>
    fun updatePassenger(passenger: Passenger): Int
    fun deletePassenger(passenger: Passenger): Int
}