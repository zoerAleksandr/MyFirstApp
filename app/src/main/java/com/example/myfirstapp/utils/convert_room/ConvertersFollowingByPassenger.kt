package com.example.myfirstapp.utils.convert_room

import androidx.room.TypeConverter
import com.example.myfirstapp.domain.entity.FollowingByPassenger
import com.google.gson.Gson

class ConvertersFollowingByPassenger {
    @TypeConverter
    fun listToJson(value: List<FollowingByPassenger>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Array<FollowingByPassenger>::class.java).toList()
}