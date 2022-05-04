package com.example.myfirstapp.domain.entity

data class Itinerary(
    val itineraryID: Long,
    var number: String?,
    var appearanceAtWork: Byte?, // Изменить тип на Calendar
    var endOfWork: Byte?, // Изменить тип на Calendar
    var restAtThePointOfTurnover: Boolean = true,
    var notes: String?,

    var followingByPassengerList: MutableList<FollowingByPassenger?>,
    var locomotiveDataList: MutableList<LocomotiveData?>,
    var trainDataList: MutableList<TrainData?>
)