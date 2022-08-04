package com.example.myfirstapp.domain.usecase.train

import com.example.myfirstapp.domain.entity.TrainData
import com.example.myfirstapp.domain.repository.IRepositoryTrain

class UpdateTrainDataUseCase(private val repository: IRepositoryTrain) {
    fun execute(trainData: TrainData): Int {
        return repository.updateTrainData(trainData)
    }
}