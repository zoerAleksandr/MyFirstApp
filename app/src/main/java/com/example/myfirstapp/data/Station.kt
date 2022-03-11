package com.example.myfirstapp.data

data class Station(
    // название станции
    var stationName: String,
    // время прибытия
    var arrivalTime: String, // Calendar
    // время отправления
    var departureTime: String // Calendar
)
