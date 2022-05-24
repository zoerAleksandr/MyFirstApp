package com.example.myfirstapp.domain.usecase.locomotive

import com.example.myfirstapp.domain.repository.IRepository
import io.reactivex.rxjava3.core.Single

class UpdateNumberLocoUseCase(private val repository: IRepository) {
    fun execute(locomotiveDataID: String, number: String?): Single<Int>{
        return repository.updateNumberLoco(locomotiveDataID, number)
    }
}