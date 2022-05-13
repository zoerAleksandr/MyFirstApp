package com.example.myfirstapp.utils.convert_room

import androidx.room.TypeConverter
import com.example.myfirstapp.domain.entity.TrainData
import com.google.gson.Gson

class ConvertersTrainData {
    @TypeConverter
    fun listToJson(value: List<TrainData>): String {
        return Gson().toJson(value).toString()
    }

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Array<TrainData>::class.java).toList()
}