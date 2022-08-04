package com.example.myfirstapp.domain.usecase.passenger

import com.example.myfirstapp.domain.entity.Passenger
import com.example.myfirstapp.domain.repository.IRepositoryPassenger

class GetPassengerListByItineraryIdUseCase(private val repository: IRepositoryPassenger) {
    fun execute(itineraryId: String): List<Passenger> {
        return repository.getPassengerListByItineraryId(itineraryId)
    }
}