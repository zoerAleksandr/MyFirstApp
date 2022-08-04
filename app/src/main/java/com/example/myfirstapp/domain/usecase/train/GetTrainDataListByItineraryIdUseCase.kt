package com.example.myfirstapp.domain.usecase.train

import com.example.myfirstapp.domain.entity.TrainData
import com.example.myfirstapp.domain.repository.IRepositoryTrain

class GetTrainDataListByItineraryIdUseCase(private val repository: IRepositoryTrain) {
    fun execute(itineraryId: String): MutableList<TrainData> {
        return repository.getListTrainData(itineraryId)
    }
}