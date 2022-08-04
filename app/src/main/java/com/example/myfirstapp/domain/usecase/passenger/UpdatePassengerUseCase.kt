package com.example.myfirstapp.domain.usecase.passenger

import com.example.myfirstapp.domain.entity.Passenger
import com.example.myfirstapp.domain.repository.IRepositoryPassenger

class UpdatePassengerUseCase(private val repository: IRepositoryPassenger) {
    fun execute(passenger: Passenger): Int {
        return repository.updatePassenger(passenger)
    }
}