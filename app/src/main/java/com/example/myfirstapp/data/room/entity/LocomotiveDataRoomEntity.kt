package com.example.myfirstapp.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myfirstapp.domain.entity.CountSections
import com.example.myfirstapp.domain.entity.TypeOfTraction
import java.util.*

@Entity(
    tableName = "locomotive"
)
data class LocomotiveDataRoomEntity(
    @PrimaryKey
    @ColumnInfo(name = "locomotive_data_id")
    val locomotiveDataID: String,
    val itineraryID: String,
    var series: String?,
    var number: Byte?,
    var typeOfTraction: TypeOfTraction,
    var countSections: CountSections,
    var startAcceptance: Calendar?, // Изменить тип на Calendar
    var endAcceptance: Calendar?, // Изменить тип на Calendar
    var startDelivery: Calendar?, // Изменить тип на Calendar
    var endDelivery: Calendar?, // Изменить тип на Calendar
    var counterElectricListRoomEntity: MutableList<String>,
    var dieselFuelListRoomEntity: MutableList<String>,
    var countBrakeShoes: Int?,
    var countExtinguishers: Int?
) {
//    fun createNewEntityLocomotiveData(locomotiveData: LocomotiveData) =
//        EntityLocomotiveData(
//            locomotiveDataID = locomotiveData.locomotiveDataID,
//            itineraryID = locomotiveData.itineraryID,
//            series = locomotiveData.series,
//            number = locomotiveData.number,
//            typeOfTraction = locomotiveData.typeOfTraction,
//            countSections = locomotiveData.countSections,
//            startAcceptance = locomotiveData.startAcceptance,
//            endAcceptance = locomotiveData.endAcceptance,
//            startDelivery = locomotiveData.startDelivery,
//            endDelivery = locomotiveData.endDelivery,
//            counterElectricListRoomEntity = locomotiveData.counterElectricListRoomEntity,
//            dieselFuelListRoomEntity = locomotiveData.dieselFuelListRoomEntity,
//            countBrakeShoes = locomotiveData.countBrakeShoes,
//            countExtinguishers = locomotiveData.countExtinguishers
//        )

}