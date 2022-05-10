package com.example.myfirstapp.domain.entity

import com.example.myfirstapp.domain.entity.Station

data class TrainData(
    val trainDataID: String,
    val itineraryID: String,
    var numberOfTrain: Int?,
    var weight: Int?,
    var wheelAxle: Int?,
    var conditionalLength: Int?,
    var stations: MutableList<String>
)
