package com.example.myfirstapp.domain.usecase.train

import com.example.myfirstapp.domain.entity.TrainData
import com.example.myfirstapp.domain.repository.IRepository
import io.reactivex.rxjava3.core.Single

class AddTrainDataUseCase(private val repository: IRepository) {
    fun execute(trainData: TrainData): Single<Long> {
        return repository.addTrainData(trainData)
    }
}