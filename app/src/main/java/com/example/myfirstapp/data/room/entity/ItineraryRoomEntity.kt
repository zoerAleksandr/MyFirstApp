package com.example.myfirstapp.data.room.entity

import androidx.room.*
import com.google.gson.Gson

@Entity(
    tableName = "itinerary"
)
data class ItineraryRoomEntity(
    @PrimaryKey()
    val itineraryID: String,
    var number: String?,
    @ColumnInfo(name = "appearance_at_work")
    var appearanceAtWork: Byte?,
    @ColumnInfo(name = "end_at_work")
    var endOfWork: Byte?,
    @ColumnInfo(name = "rest_at_the_point_of_turnover")
    var restAtThePointOfTurnover: Boolean,
    var notes: String?,
    @ColumnInfo(name = "following_by_passenger_list")
    var followingByPassengerList: MutableList<String>,
    @ColumnInfo(name = "locomotive_data_list")
    var locomotiveDataList: MutableList<String>,
    @ColumnInfo(name = "train_data_list")
    var trainDataList: MutableList<String>,
) {
//
//    fun createNewEntityItinerary(itinerary: Itinerary) =
//        ItineraryRoomEntity(
//            itineraryID = itinerary.itineraryID,
//            number = itinerary.number,
//            appearanceAtWork = itinerary.appearanceAtWork,
//            endOfWork = itinerary.endOfWork,
//            restAtThePointOfTurnover = itinerary.restAtThePointOfTurnover,
//            notes = itinerary.notes,
//            followingByPassengerList = EntityFollowingByPassenger.toEntityFollowingByPassengerList(
//                itinerary.followingByPassengerList
//            ),
//            locomotiveDataList = EntityLocomotiveData.toEntityLocomotiveDataList(itinerary.locomotiveDataList),
//            trainDataList = EntityTrainData.toEntityTrainDataList(itinerary.trainDataList)
//        )
//
}