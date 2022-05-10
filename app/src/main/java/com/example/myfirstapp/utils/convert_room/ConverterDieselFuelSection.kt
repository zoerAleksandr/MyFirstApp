package com.example.myfirstapp.utils.convert_room

import androidx.room.TypeConverter
import com.example.myfirstapp.data.room.entity.DieselFuelSectionRoomEntity
import com.google.gson.Gson

class ConverterDieselFuelSection {
    @TypeConverter
    fun listToJson(value: List<DieselFuelSectionRoomEntity>) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Array<DieselFuelSectionRoomEntity>::class.java).toList()
}