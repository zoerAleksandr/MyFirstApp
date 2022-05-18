package com.example.myfirstapp.utils.convert_room

import androidx.room.TypeConverter
import com.example.myfirstapp.domain.entity.DieselFuelSection
import com.google.gson.Gson

class ConverterDieselFuelSectionList {
    @TypeConverter
    fun listToJson(value: List<DieselFuelSection>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Array<DieselFuelSection>::class.java).toList()
}