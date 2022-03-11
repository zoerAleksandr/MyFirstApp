package com.example.myfirstapp.data

data class TrainData(
    // номер поезда
    var numberOfTrain: Int,
    // вес
    var weight: Int,
    // оси
    var wheelAxle: Int,
    // условная длина
    var conditionalLength: Int,
    // станции
    var stations: List<Itinerary.Station>
)
