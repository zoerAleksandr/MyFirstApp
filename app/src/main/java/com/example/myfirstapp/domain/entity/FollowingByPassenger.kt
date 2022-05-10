package com.example.myfirstapp.domain.entity

data class FollowingByPassenger(
    var followingByPassengerID: String,
    var itineraryID: String,
    var departureTime: String?, // Calendar
    var arrivalTime: String?, // Calendar
    var departureStation: String?,
    var arrivalStation: String?,
    var numberOfTrain: Int?
)
