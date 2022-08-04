package com.example.myfirstapp.utils.convert_room

import androidx.room.TypeConverter
import com.example.myfirstapp.domain.entity.LocomotiveData
import com.google.gson.Gson

class ConvertersLocomotiveData {
    @TypeConverter
    fun listToJson(value: List<LocomotiveData>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String?) =
        Gson().fromJson(value, Array<LocomotiveData>::class.java).toList()
}