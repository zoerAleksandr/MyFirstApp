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
        roomEntity.id,
        roomEntity.itineraryId,
        roomEntity.numberOfTrain,
        roomEntity.weight,
        roomEntity.wheelAxle,
        roomEntity.conditionalLength,
    )
}

fun toTrainDataRoomEntityList(trainDataList: List<TrainData>): MutableList<TrainDataRoomEntity> {
    val list = mutableListOf<TrainDataRoomEntity>()
    for (item in trainDataList) {
        list.add(toTrainDataRoomEntity(item))
    }
    return list
}

fun toTrainDataRoomEntity(trainData: TrainData): TrainDataRoomEntity {
    return TrainDataRoomEntity(
        trainData.id,
        trainData.itineraryID,
        trainData.numberOfTrain,
        trainData.weight,
        trainData.wheelAxle,
        trainData.conditionalLength,
    )
}
