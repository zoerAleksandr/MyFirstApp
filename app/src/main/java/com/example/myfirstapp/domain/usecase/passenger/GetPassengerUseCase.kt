package com.example.myfirstapp.domain.usecase.passenger

import com.example.myfirstapp.domain.entity.Passenger
import com.example.myfirstapp.domain.repository.IRepositoryPassenger

class GetPassengerUseCase(private val repository: IRepositoryPassenger) {
    fun execute(passengerId: String): Passenger {
        return repository.getPassenger(passengerId)
    }
}