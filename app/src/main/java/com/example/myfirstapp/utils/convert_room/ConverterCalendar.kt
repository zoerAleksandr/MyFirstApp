package com.example.myfirstapp.utils.convert_room

import androidx.room.TypeConverter
import com.google.gson.Gson
import java.util.*

class ConverterCalendar {
    @TypeConverter
    fun listToJson(value: Calendar?): String? = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String?): Calendar? =
        Gson().fromJson(value, Calendar::class.java)
}