package com.example.myfirstapp.domain.entity

data class FollowingByPassenger(
    var followingByPassengerID: Long,
    var itineraryID: Long,
    var departureTime: String?, // Calendar
    var arrivalTime: String?, // Calendar
    var departureStation: String?,
    var arrivalStation: String?,
    var numberOfTrain: Int?
)
