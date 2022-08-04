package com.example.myfirstapp.domain.repository

import com.example.myfirstapp.domain.entity.TrainData

interface IRepositoryTrain {
    fun addTrainData(trainData: TrainData): Long
    fun getTrainData(trainDataID: String): TrainData
    fun getListTrainData(itineraryID: String): MutableList<TrainData>
    fun updateTrainData(trainData: TrainData): Int
    fun deleteTrainData(trainData: TrainData): Int
}