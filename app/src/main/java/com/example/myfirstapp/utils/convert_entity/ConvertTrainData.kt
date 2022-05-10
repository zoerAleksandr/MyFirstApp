package com.example.myfirstapp.utils.convert_entity

import com.example.myfirstapp.data.room.entity.TrainDataRoomEntity
import com.example.myfirstapp.domain.entity.TrainData

fun toTrainDataList(
    roomEntityList: MutableList<TrainDataRoomEntity>
): MutableList<TrainData> {
    val list = mutableListOf<TrainData>()
    for (item in roomEntityList) {
        list.add(toTrainData(item))
    }
    return list
}

fun toTrainData(roomEntity: TrainDataRoomEntity): TrainData {
    return TrainData(
        roomEntity.trainDataID,
        roomEntity.itineraryID,
        roomEntity.numberOfTrain,
        roomEntity.weight,
        roomEntity.wheelAxle,
        roomEntity.conditionalLength,
        roomEntity.stations
    )
}

fun toTrainDataRoomEntity(trainData: TrainData): TrainDataRoomEntity {
    return TrainDataRoomEntity(
        trainData.trainDataID,
        trainData.itineraryID,
        trainData.numberOfTrain,
        trainData.weight,
        trainData.wheelAxle,
        trainData.conditionalLength,
        trainData.stations
    )
}
