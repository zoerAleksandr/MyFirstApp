package com.example.myfirstapp.domain.usecase.locomotive

import com.example.myfirstapp.domain.entity.TypeOfTraction
import com.example.myfirstapp.domain.repository.IRepository
import io.reactivex.rxjava3.core.Single

class UpdateTypeOfTractionUseCase(private val repository: IRepository) {
    fun execute(locomotiveDataId: String, typeOfTraction: TypeOfTraction): Single<Int>{
        return repository.updateTypeOfTraction(locomotiveDataId, typeOfTraction)
    }
}