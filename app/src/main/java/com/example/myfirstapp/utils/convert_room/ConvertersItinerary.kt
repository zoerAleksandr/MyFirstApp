package com.example.myfirstapp.utils.convert_room

import androidx.room.TypeConverter
import com.example.myfirstapp.data.room.entity.ItineraryRoomEntity
import com.google.gson.Gson

class ConvertersItinerary {
    @TypeConverter
    fun listToJson(value: List<ItineraryRoomEntity>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Array<ItineraryRoomEntity>::class.java).toList()
}