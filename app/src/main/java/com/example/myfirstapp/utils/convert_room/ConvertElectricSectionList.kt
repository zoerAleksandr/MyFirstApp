package com.example.myfirstapp.utils.convert_room

import androidx.room.TypeConverter
import com.example.myfirstapp.domain.entity.ElectricSection
import com.google.gson.Gson

class ConvertElectricSectionList {
    @TypeConverter
    fun listToJson(value: List<ElectricSection>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Array<ElectricSection>::class.java).toList()
}