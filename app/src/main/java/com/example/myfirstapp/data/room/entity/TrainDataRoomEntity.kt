package com.example.myfirstapp.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.myfirstapp.domain.entity.TrainData
import com.google.gson.Gson

@Entity(
    tableName = "train"
)
data class TrainDataRoomEntity(
    @PrimaryKey
    val trainDataID: String,
    val itineraryID: String,
    var numberOfTrain: Int?,
    var weight: Int?,
    var wheelAxle: Int?,
    var conditionalLength: Int?,
    var stations: MutableList<String>
) {
//    private fun createNewEntityTrainData(trainData: TrainData) =
//        TrainDataRoomEntity(
//            trainDataID = trainData.trainDataID,
//            itineraryID = trainData.itineraryID,
//            numberOfTrain = trainData.numberOfTrain,
//            weight = trainData.weight,
//            wheelAxle = trainData.wheelAxle,
//            conditionalLength = trainData.conditionalLength,
//            stations = EntityStation.toEntityStationList(trainData.stations)
//        )
//
//    fun toEntityTrainDataList(trainDataList: MutableList<TrainData?>): MutableList<TrainDataRoomEntity?> {
//        val list = mutableListOf<TrainDataRoomEntity?>()
//        for (item in trainDataList) {
//            list.add(item?.let { createNewEntityTrainData(it) })
//        }
//        return list
//    }

}

class ConvertersTrainData {
    @TypeConverter
    fun listToJson(value: List<TrainDataRoomEntity>?) = Gson().toJson(value).toString()

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Array<TrainDataRoomEntity>::class.java).toList()
}
