package com.example.myfirstapp.utils.convert_room

import androidx.room.TypeConverter
import com.example.myfirstapp.domain.entity.CountSections
import com.google.gson.Gson

class ConverterCountSection {
    @TypeConverter
    fun listToJson(value: CountSections?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, CountSections::class.java)
}