package com.example.myfirstapp.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.myfirstapp.domain.entity.TrainData
import com.google.gson.Gson

@Entity(
    tableName = "train"
)
data class EntityTrainData(
    @PrimaryKey
    val trainDataID: Long,
    val itineraryID: Long,
    var numberOfTrain: Int?,
    var weight: Int?,
    var wheelAxle: Int?,
    var conditionalLength: Int?,
    var stations: MutableList<EntityStation?>
) {
    companion object {
        private fun createNewEntityTrainData(trainData: TrainData) =
            EntityTrainData(
                trainDataID = trainData.trainDataID,
                itineraryID = trainData.itineraryID,
                numberOfTrain = trainData.numberOfTrain,
                weight = trainData.weight,
                wheelAxle = trainData.wheelAxle,
                conditionalLength = trainData.conditionalLength,
                stations = EntityStation.toEntityStationList(trainData.stations)
            )

        fun toEntityTrainDataList(trainDataList: MutableList<TrainData?>): MutableList<EntityTrainData?> {
            val list = mutableListOf<EntityTrainData?>()
            for (item in trainDataList) {
                list.add(item?.let { createNewEntityTrainData(it) })
            }
            return list
        }

        fun toTrainDataList(trainDataList: MutableList<EntityTrainData?>): MutableList<TrainData?> {
            val list = mutableListOf<TrainData?>()
            for (item in trainDataList) {
                if (item != null) {
                    list.add(item.toTrainData())
                }
            }
            return list
        }
    }

    fun toTrainData(): TrainData = TrainData(
        trainDataID = trainDataID,
        itineraryID = itineraryID,
        numberOfTrain = numberOfTrain,
        weight = weight,
        wheelAxle = wheelAxle,
        conditionalLength = conditionalLength,
        stations = EntityStation.toStationList(stations)
    )
}

class ConvertersTrainData {
    @TypeConverter
    fun listToJson(value: List<EntityTrainData>?) = Gson().toJson(value).toString()

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Array<EntityTrainData>::class.java).toList()
}
