package com.example.myfirstapp.domain.usecase.passenger

import com.example.myfirstapp.domain.entity.FollowingByPassenger
import com.example.myfirstapp.domain.repository.IRepository
import io.reactivex.rxjava3.core.Single

class AddPassengerUseCase(private val repository: IRepository) {
    fun execute(passenger: FollowingByPassenger): Single<Long> {
        return repository.addFallowingByPassenger(passenger)
    }
}