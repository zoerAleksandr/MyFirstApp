package com.example.myfirstapp.utils.convert_room

import androidx.room.TypeConverter
import com.example.myfirstapp.data.room.entity.FollowingByPassengerRoomEntity
import com.google.gson.Gson


class ConvertersFollowingByPassenger {
    @TypeConverter
    fun listToJson(value: List<FollowingByPassengerRoomEntity>) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Array<FollowingByPassengerRoomEntity>::class.java).toList()
}