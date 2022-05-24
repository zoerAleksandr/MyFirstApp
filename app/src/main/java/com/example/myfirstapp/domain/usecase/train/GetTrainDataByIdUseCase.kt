package com.example.myfirstapp.domain.usecase.train

import com.example.myfirstapp.domain.entity.TrainData
import com.example.myfirstapp.domain.repository.IRepository
import io.reactivex.rxjava3.core.Single

class GetTrainDataByIdUseCase(private val repository: IRepository) {
    fun execute(trainDataId: String): Single<TrainData>{
        return repository.getTrainData(trainDataId)
    }
}