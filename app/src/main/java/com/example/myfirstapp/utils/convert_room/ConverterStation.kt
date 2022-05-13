package com.example.myfirstapp.utils.convert_room

import androidx.room.TypeConverter
import com.example.myfirstapp.domain.entity.Station
import com.google.gson.Gson

class ConverterStation {
    @TypeConverter
    fun listToJson(value: List<Station>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Array<Station>::class.java).toList()
}