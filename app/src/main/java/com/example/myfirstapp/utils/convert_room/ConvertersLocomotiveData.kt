package com.example.myfirstapp.utils.convert_room

import androidx.room.TypeConverter
import com.example.myfirstapp.data.room.entity.LocomotiveDataRoomEntity
import com.google.gson.Gson

class ConvertersLocomotiveData {
    @TypeConverter
    fun listToJson(value: List<LocomotiveDataRoomEntity>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String?) =
        Gson().fromJson(value, Array<LocomotiveDataRoomEntity>::class.java).toList()
}