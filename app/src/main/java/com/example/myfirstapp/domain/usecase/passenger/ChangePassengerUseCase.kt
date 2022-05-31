package com.example.myfirstapp.domain.usecase.passenger

import com.example.myfirstapp.domain.entity.FollowingByPassenger
import com.example.myfirstapp.domain.repository.IRepository
import io.reactivex.rxjava3.core.Single

class ChangePassengerUseCase(private val repository: IRepository) {
    fun execute(passenger: FollowingByPassenger): Single<Int>{
        return repository.changeFallowingByPassenger(passenger)
    }
}