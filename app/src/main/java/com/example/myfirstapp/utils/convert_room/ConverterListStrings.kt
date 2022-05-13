package com.example.myfirstapp.utils.convert_room

import androidx.room.TypeConverter
import com.google.gson.Gson

class ConverterListStrings {
    @TypeConverter
    fun listToJson(value: List<String>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Array<String>::class.java).toList()
}