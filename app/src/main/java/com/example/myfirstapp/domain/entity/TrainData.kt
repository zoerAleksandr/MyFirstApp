package com.example.myfirstapp.domain.entity

import com.example.myfirstapp.domain.entity.Station

data class TrainData(
    val trainDataID: Long,
    val itineraryID: Long,
    var numberOfTrain: Int?,
    var weight: Int?,
    var wheelAxle: Int?,
    var conditionalLength: Int?,
    var stations: MutableList<Station?>
)
