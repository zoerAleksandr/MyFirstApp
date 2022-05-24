package com.example.myfirstapp.domain.usecase.locomotive

import com.example.myfirstapp.domain.repository.IRepository
import io.reactivex.rxjava3.core.Single

class UpdateSeriesLocoUseCase(private val repository: IRepository) {
    fun execute(locomotiveDataID: String ,series: String?): Single<Int>{
        return repository.updateSeriesLoco(locomotiveDataID, series)
    }
}