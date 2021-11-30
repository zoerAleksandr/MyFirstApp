package com.example.myfirstapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

// данный класс содержит все данные об одном маршруте машиниста
// данный класс является родителем таких классов как FollowingByPassenger ("Следование пассажиром"),
//  LocomotiveData ("Данные о локомотиве"), TrainData ("Данные о поезде")
@Parcelize
data class Itinerary(

    private val itineraryID: String = UUID.randomUUID().toString(),
    //var number: String = "0",
    var appearanceAtWork: Byte = 8, // Изменить тип на Calendar
    var endOfWork: Byte = 11, // Изменить тип на Calendar
    var restAtThePointOfTurnover: Boolean = true,
    var notes: String = "Примечания",

    var followingByPassenger: FollowingByPassenger = FollowingByPassenger(),
    var locomotiveData: LocomotiveData = LocomotiveData(),
    var trainData: TrainData = TrainData()

) : Parcelable {

    // Чтобы сделать поля не обязательные для заполнения, со значениями по умолчанию
    // их необходимо вынести в тело класса
    // Пример:
    var number: String = "0"
    fun getItineraryID() = this.itineraryID

    fun getAppearanceAtWork(): String {
        return appearanceAtWork.toString()

    }

    @Parcelize
    class FollowingByPassenger : Parcelable {
    }

    @Parcelize
    data class LocomotiveData(
        var series: String = "2эс4к",
        var number: Byte = 100
    ) : Parcelable {

    }

    @Parcelize
    data class TrainData(
        var stationStart: String = "Лужская",
        var stationFinish: String = "Луга",
    ) : Parcelable {

    }
}