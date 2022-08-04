package com.example.myfirstapp.data.room.repository

import com.example.myfirstapp.domain.entity.TrainData
import com.example.myfirstapp.domain.repository.IRepositoryTrain
import io.reactivex.rxjava3.core.Single

class RoomRepositoryTrain : IRepositoryTrain {
    override fun addTrainData(trainData: TrainData): Long {
        TODO("Not yet implemented")
    }

    override fun getTrainData(trainDataID: String): TrainData {
        TODO("Not yet implemented")
    }

    override fun getListTrainData(itineraryID: String): MutableList<TrainData> {
        TODO("Not yet implemented")
    }

    override fun updateTrainData(trainData: TrainData): Int {
        TODO("Not yet implemented")
    }

    override fun deleteTrainData(trainData: TrainData): Int {
        TODO("Not yet implemented")
    }
}