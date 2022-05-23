package com.example.myfirstapp.domain.usecase.locomotive

import com.example.myfirstapp.domain.repository.IRepository
import io.reactivex.rxjava3.core.Single

class UpdateExtinguishersUseCase(private val repository: IRepository) {
    fun execute(locomotiveDataID: String, count: Int?): Single<Int> {
        return repository.updateExtinguishers(locomotiveDataID, count)
    }
}