package com.example.myfirstapp.data

data class TrainData(
    // ID
    val trainDataID: Long,
    // itineraryID
    val itineraryID: Long,
    // номер поезда
    var numberOfTrain: Int?,
    // вес
    var weight: Int?,
    // оси
    var wheelAxle: Int?,
    // условная длина
    var conditionalLength: Int?,
    // станции
    var stations: MutableList<Station?>
)
