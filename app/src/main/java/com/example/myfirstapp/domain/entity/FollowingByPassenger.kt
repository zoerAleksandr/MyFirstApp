package com.example.myfirstapp.domain.entity

import java.util.*

data class FollowingByPassenger(
    var followingByPassengerID: String,
    var itineraryID: String,
    var departureTime: Calendar?,
    var arrivalTime: Calendar?,
    var departureStation: String?,
    var arrivalStation: String?,
    var numberOfTrain: String?,
    var notes: String?
)
