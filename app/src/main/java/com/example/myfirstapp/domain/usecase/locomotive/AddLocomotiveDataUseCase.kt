package com.example.myfirstapp.domain.usecase.locomotive

import com.example.myfirstapp.domain.entity.LocomotiveData
import com.example.myfirstapp.domain.repository.IRepository
import io.reactivex.rxjava3.core.Single

class AddLocomotiveDataUseCase(private val repository: IRepository) {
    fun execute(locomotiveData: LocomotiveData): Single<Long>{
       return repository.addLocomotiveData(locomotiveData)
    }
}