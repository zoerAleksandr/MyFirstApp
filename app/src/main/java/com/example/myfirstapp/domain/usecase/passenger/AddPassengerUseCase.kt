package com.example.myfirstapp.domain.usecase.passenger

import com.example.myfirstapp.domain.entity.Passenger
import com.example.myfirstapp.domain.repository.IRepositoryPassenger

class AddPassengerUseCase(private val repository: IRepositoryPassenger) {
    fun execute(passenger: Passenger): String {
        return repository.addPassenger(passenger)
    }
}