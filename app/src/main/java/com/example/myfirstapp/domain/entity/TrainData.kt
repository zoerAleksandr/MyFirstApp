package com.example.myfirstapp.domain.entity

data class TrainData(
    val id: String,
    val itineraryID: String,
    var numberOfTrain: String?,
    var weight: String?,
    var wheelAxle: String?,
    var conditionalLength: String?,
)
