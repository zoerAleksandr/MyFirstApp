package com.example.myfirstapp.data

import com.example.myfirstapp.vm.CountSections
import com.example.myfirstapp.vm.TypeOfTraction
import java.util.*

data class LocomotiveData(
    // ID
    val locomotiveDataID: Long,
    // itineraryID
    val itineraryID: Long,
    // серия
    var series: String?,
    // номер
    var number: Byte?,
    // тип тяги
    var typeOfTraction: TypeOfTraction,
    // количество секций
    var countSections: CountSections,

    // начало приемки
    var startAcceptance: String?, // Изменить тип на Calendar
    // окончание приемки
    var endAcceptance: String?, // Изменить тип на Calendar
    // начало сдачи
    var startDelivery: String?, // Изменить тип на Calendar
    // окончание сдачи
    var endDelivery: String?, // Изменить тип на Calendar

    // счетчики
    var counterElectricList: MutableList<CounterElectricSection>?,
    // топливо
    var dieselFuelList: MutableList<DieselFuelSection>?,

    // количество башмаков
    var countBrakeShoes: Int?,
    // количество огнетушителей
    var countExtinguishers: Int?
)
