package com.example.myfirstapp.utils.convert_room

import androidx.room.TypeConverter
import com.example.myfirstapp.domain.entity.TypeOfTraction
import com.google.gson.Gson

class ConverterTypeOfTraction {
    @TypeConverter
    fun listToJson(value: TypeOfTraction?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, TypeOfTraction::class.java)
}