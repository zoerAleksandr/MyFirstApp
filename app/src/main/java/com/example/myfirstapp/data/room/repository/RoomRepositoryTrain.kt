package com.example.myfirstapp.data.room.repository

import com.example.myfirstapp.data.room.ItineraryDAO
import com.example.myfirstapp.domain.entity.TrainData
import com.example.myfirstapp.domain.repository.IRepositoryTrain
import com.example.myfirstapp.utils.convert_entity.toTrainDataRoomEntity
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RoomRepositoryTrain : IRepositoryTrain, KoinComponent {
    private val localStorage: ItineraryDAO by inject()

    override fun addTrainData(trainData: TrainData): Long {
        return localStorage.addTrainData(
            toTrainDataRoomEntity(trainData)
        )
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