package com.example.myfirstapp.utils.convert_room

import androidx.room.TypeConverter
import com.example.myfirstapp.data.room.entity.CounterElectricSectionRoomEntity
import com.google.gson.Gson


class ConverterCounterElectricSection {
    @TypeConverter
    fun listToJson(value: List<CounterElectricSectionRoomEntity>) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Array<CounterElectricSectionRoomEntity>::class.java).toList()
}