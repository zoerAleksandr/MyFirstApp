package com.example.myfirstapp.utils.convert_room

import androidx.room.TypeConverter
import com.example.myfirstapp.data.room.entity.StationRoomEntity
import com.google.gson.Gson

class ConverterStation {
    @TypeConverter
    fun listToJson(value: List<StationRoomEntity>) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Array<StationRoomEntity>::class.java).toList()
}