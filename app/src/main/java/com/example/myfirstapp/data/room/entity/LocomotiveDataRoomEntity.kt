package com.example.myfirstapp.data.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.example.myfirstapp.domain.entity.CountSections
import com.example.myfirstapp.domain.entity.DieselFuelSection
import com.example.myfirstapp.domain.entity.ElectricSection
import com.example.myfirstapp.domain.entity.TypeOfTraction
import java.util.*

@Entity(
    tableName = "locomotive",
    foreignKeys = [
        ForeignKey(
            entity = ItineraryRoomEntity::class,
            parentColumns = ["id"],
            childColumns = ["itineraryId"],
            onDelete = CASCADE,
            onUpdate = CASCADE
        )
    ],
    primaryKeys = ["itineraryId", "id"]
)
data class LocomotiveDataRoomEntity(
    val id: String,
    val itineraryId: String,
    var series: String?,
    var number: String?,
    var typeOfTraction: TypeOfTraction,
    var countSections: CountSections,
    var startAcceptance: Calendar?,
    var endAcceptance: Calendar?,
    var startDelivery: Calendar?,
    var endDelivery: Calendar?,
    var electricSectionList: MutableList<ElectricSection>,
    var dieselFuelSectionList: MutableList<DieselFuelSection>,
    var countBrakeShoes: Int?,
    var countExtinguishers: Int?
)