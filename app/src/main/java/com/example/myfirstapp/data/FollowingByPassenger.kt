package com.example.myfirstapp.data

import java.util.*

data class FollowingByPassenger(
    // ID
    var followingByPassengerID: Long,
    // itineraryID
    var itineraryID: Long,
    // время отправления
    var departureTime: String?, // Calendar
    // время прибытия
    var arrivalTime: String?, // Calendar
    // станция отправления
    var departureStation: String?,
    // станция прибытия
    var arrivalStation: String?,
    // номер поезда
    var numberOfTrain: Int?
)
