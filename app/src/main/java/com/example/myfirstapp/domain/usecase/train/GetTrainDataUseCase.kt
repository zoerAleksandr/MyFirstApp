package com.example.myfirstapp.domain.usecase.train

import com.example.myfirstapp.domain.entity.TrainData
import com.example.myfirstapp.domain.repository.IRepositoryTrain

class GetTrainDataUseCase(private val repository: IRepositoryTrain) {
    fun execute(trainDataId: String): TrainData {
        return repository.getTrainData(trainDataId)
    }
}