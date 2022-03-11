package com.example.myfirstapp.data

import com.example.myfirstapp.vm.CountSections
import com.example.myfirstapp.vm.TypeOfTraction

data class LocomotiveData(
    // серия
    var series: String = "2эс4к",
    // номер
    var number: Byte = 100,
    // тип тяги
    var typeOfTraction: TypeOfTraction = TypeOfTraction.ElectricLocomotive,
    // количество секций
    var countSections: CountSections = CountSections.TwoSection,

    // начало приемки
    var startAcceptance: String, // Изменить тип на Calendar
    // окончание приемки
    var endAcceptance: String, // Изменить тип на Calendar
    // начало сдачи
    var startDelivery: String, // Изменить тип на Calendar
    // окончание сдачи
    var endDelivery: String, // Изменить тип на Calendar

    // счетчики
    var counterElectricList: MutableList<CounterElectricSection>,
    // топливо
    var dieselFuelList: MutableList<DieselFuelSection>,

    // количество башмаков
    var countBrakeShoes: Int,
    // количество огнетушителей
    var countExtinguishers: Int
)
