package com.example.myfirstapp.domain.entity

data class Itinerary(
    val itineraryID: String,
    var number: String?,
    var appearanceAtWork: Byte?, // Изменить тип на Calendar
    var endOfWork: Byte?, // Изменить тип на Calendar
    var restAtThePointOfTurnover: Boolean = true,
    var notes: String?,

    var followingByPassengerList: MutableList<String>,
    var locomotiveDataList: MutableList<String>,
    var trainDataList: MutableList<String>
)