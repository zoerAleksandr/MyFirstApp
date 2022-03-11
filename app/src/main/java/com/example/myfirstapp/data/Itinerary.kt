package com.example.myfirstapp.data

import java.util.*

data class Itinerary(
    // ID
    private val itineraryID: String = UUID.randomUUID().toString(),
    // явка на работу
    var appearanceAtWork: Byte = 8, // Изменить тип на Calendar
    // окончание работы
    var endOfWork: Byte = 11, // Изменить тип на Calendar
    // отдых в ПО
    var restAtThePointOfTurnover: Boolean = true,
    // примечания
    var notes: String = "Примечания",

    // следование пассажиром
    var followingByPassengerList: MutableList<FollowingByPassenger>,
    // данные о локомотиве
    var locomotiveDataList: MutableList<LocomotiveData>,
    // данные о поезде
    var trainDataList: MutableList<TrainData>

) {
    // Чтобы сделать поля не обязательные для заполнения, со значениями по умолчанию
    // их необходимо вынести в тело класса
    // Пример:
    var number: String = "0"
    fun getItineraryID() = this.itineraryID

    fun getAppearanceAtWork(): String {
        return appearanceAtWork.toString()
    }
}