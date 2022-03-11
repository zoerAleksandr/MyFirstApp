package com.example.myfirstapp.data

data class FollowingByPassenger(
    // время отправления
    var departureTime: String, // Calendar
    // время прибытия
    var arrivalTime: String, // Calendar
    // станция отправления
    var departureStation: String,
    // станция прибытия
    var arrivalStation: String,
    // номер поезда
    var numberOfTrain: Int
)
