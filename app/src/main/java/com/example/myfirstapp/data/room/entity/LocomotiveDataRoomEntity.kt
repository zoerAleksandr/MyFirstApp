package com.example.myfirstapp.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.myfirstapp.domain.entity.CountSections
import com.example.myfirstapp.domain.entity.LocomotiveData
import com.example.myfirstapp.domain.entity.TypeOfTraction
import com.google.gson.Gson

@Entity(
    tableName = "locomotive"
)
data class EntityLocomotiveData(
    @PrimaryKey
    @ColumnInfo(name = "locomotive_data_id")
    val locomotiveDataID: Long,
    val itineraryID: Long,
    var series: String?,
    var number: Byte?,
    var typeOfTraction: TypeOfTraction,
    var countSections: CountSections,
    var startAcceptance: String?, // Изменить тип на Calendar
    var endAcceptance: String?, // Изменить тип на Calendar
    var startDelivery: String?, // Изменить тип на Calendar
    var endDelivery: String?, // Изменить тип на Calendar
    var counterElectricListRoomEntity: MutableList<CounterElectricSectionRoomEntity>?,
    var dieselFuelListRoomEntity: MutableList<DieselFuelSectionRoomEntity>?,
    var countBrakeShoes: Int?,
    var countExtinguishers: Int?
) {
    companion object {
        fun createNewEntityLocomotiveData(locomotiveData: LocomotiveData) =
            EntityLocomotiveData(
                locomotiveDataID = locomotiveData.locomotiveDataID,
                itineraryID = locomotiveData.itineraryID,
                series = locomotiveData.series,
                number = locomotiveData.number,
                typeOfTraction = locomotiveData.typeOfTraction,
                countSections = locomotiveData.countSections,
                startAcceptance = locomotiveData.startAcceptance,
                endAcceptance = locomotiveData.endAcceptance,
                startDelivery = locomotiveData.startDelivery,
                endDelivery = locomotiveData.endDelivery,
                counterElectricListRoomEntity = locomotiveData.counterElectricListRoomEntity,
                dieselFuelListRoomEntity = locomotiveData.dieselFuelListRoomEntity,
                countBrakeShoes = locomotiveData.countBrakeShoes,
                countExtinguishers = locomotiveData.countExtinguishers
            )

        fun toEntityLocomotiveDataList(locomotiveDataList: MutableList<LocomotiveData?>): MutableList<EntityLocomotiveData?> {
            val list = mutableListOf<EntityLocomotiveData?>()
            for (item in locomotiveDataList) {
                list.add(item?.let { createNewEntityLocomotiveData(it) })
            }
            return list
        }

        fun toLocomotiveDataList(locomotiveDataList: MutableList<EntityLocomotiveData?>): MutableList<LocomotiveData?> {
            val list = mutableListOf<LocomotiveData?>()
            for (item in locomotiveDataList) {
                if (item != null) {
                    list.add(item.toLocomotiveData())
                }
            }
            return list
        }
    }

    fun toLocomotiveData(): LocomotiveData = LocomotiveData(
        locomotiveDataID = locomotiveDataID,
        itineraryID = itineraryID,
        series = series,
        number = number,
        typeOfTraction = typeOfTraction,
        countSections = countSections,
        startAcceptance = startAcceptance,
        endAcceptance = endAcceptance,
        startDelivery = startDelivery,
        endDelivery = endDelivery,
        counterElectricListRoomEntity = counterElectricListRoomEntity,
        dieselFuelListRoomEntity = dieselFuelListRoomEntity,
        countBrakeShoes = countBrakeShoes,
        countExtinguishers = countExtinguishers
    )
}

class ConvertersLocomotiveData {
    @TypeConverter
    fun listToJson(value: List<EntityLocomotiveData>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Array<EntityLocomotiveData>::class.java).toList()
}

class ConverterDieselFuelSection {
    @TypeConverter
    fun listToJson(value: List<DieselFuelSectionRoomEntity>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Array<DieselFuelSectionRoomEntity>::class.java).toList()
}

class ConverterCounterElectricSection {
    @TypeConverter
    fun listToJson(value: List<CounterElectricSectionRoomEntity>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Array<CounterElectricSectionRoomEntity>::class.java).toList()
}

class ConverterCountSection {
    @TypeConverter
    fun listToJson(value: CountSections?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, CountSections::class.java)
}

class ConverterTypeOfTraction {
    @TypeConverter
    fun listToJson(value: TypeOfTraction?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, TypeOfTraction::class.java)
}