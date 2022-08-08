package com.example.myfirstapp.utils.convert_room

import androidx.room.TypeConverter
import com.example.myfirstapp.data.room.entity.PassengerRoomEntity
import com.google.gson.Gson

class ConvertersFollowingByPassenger {
    @TypeConverter
    fun listToJson(value: List<PassengerRoomEntity>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Array<PassengerRoomEntity>::class.java).toList()
}