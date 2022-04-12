package com.example.myfirstapp.data

data class Itinerary(
    // ID
    val itineraryID: Long,
    // номер маршрута
    var number: String?,
    // явка на работу
    var appearanceAtWork: Byte?, // Изменить тип на Calendar
    // окончание работы
    var endOfWork: Byte?, // Изменить тип на Calendar
    // отдых в ПО
    var restAtThePointOfTurnover: Boolean = true,
    // примечания
    var notes: String?,

    // следование пассажиром
    var followingByPassengerList: MutableList<FollowingByPassenger?>,
    // данные о локомотиве
    var locomotiveDataList: MutableList<LocomotiveData?>,
    // данные о поезде
    var trainDataList: MutableList<TrainData?>

) {
    // Чтобы сделать поля не обязательные для заполнения, со значениями по умолчанию
    // их необходимо вынести в тело класса
    // Пример:
//    fun getItineraryID() = this.itineraryID

    fun getAppearanceAtWork(): String {
        return appearanceAtWork.toString()
    }
}