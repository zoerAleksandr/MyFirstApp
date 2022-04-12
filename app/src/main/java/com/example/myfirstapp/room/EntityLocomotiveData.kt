package com.example.myfirstapp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.myfirstapp.data.CounterElectricSection
import com.example.myfirstapp.data.DieselFuelSection
import com.example.myfirstapp.data.LocomotiveData
import com.example.myfirstapp.vm.CountSections
import com.example.myfirstapp.vm.TypeOfTraction
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
    var counterElectricList: MutableList<CounterElectricSection>?,
    var dieselFuelList: MutableList<DieselFuelSection>?,
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
                counterElectricList = locomotiveData.counterElectricList,
                dieselFuelList = locomotiveData.dieselFuelList,
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
        counterElectricList = counterElectricList,
        dieselFuelList = dieselFuelList,
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
    fun listToJson(value: List<DieselFuelSection>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Array<DieselFuelSection>::class.java).toList()
}

class ConverterCounterElectricSection {
    @TypeConverter
    fun listToJson(value: List<CounterElectricSection>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Array<CounterElectricSection>::class.java).toList()
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