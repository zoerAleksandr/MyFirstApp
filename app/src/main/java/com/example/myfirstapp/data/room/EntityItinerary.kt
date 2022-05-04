package com.example.myfirstapp.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myfirstapp.domain.Itinerary

@Entity(
    tableName = "itinerary"
)
data class EntityItinerary(
    @PrimaryKey(autoGenerate = true)
    val itineraryID: Long,
    var number: String?,
    @ColumnInfo(name = "appearance_at_work")
    var appearanceAtWork: Byte?,
    @ColumnInfo(name = "end_at_work")
    var endOfWork: Byte?,
    @ColumnInfo(name = "rest_at_the_point_of_turnover")
    var restAtThePointOfTurnover: Boolean,
    var notes: String?,
    @ColumnInfo(name = "following_by_passenger_list")
    var followingByPassengerList: MutableList<EntityFollowingByPassenger?>,
    @ColumnInfo(name = "locomotive_data_list")
    var locomotiveDataList: MutableList<EntityLocomotiveData?>,
    @ColumnInfo(name = "train_data_list")
    var trainDataList: MutableList<EntityTrainData?>,
) {

    companion object {
        fun createNewEntityItinerary(itinerary: Itinerary) =
            EntityItinerary(
                itineraryID = itinerary.itineraryID,
                number = itinerary.number,
                appearanceAtWork = itinerary.appearanceAtWork,
                endOfWork = itinerary.endOfWork,
                restAtThePointOfTurnover = itinerary.restAtThePointOfTurnover,
                notes = itinerary.notes,
                followingByPassengerList = EntityFollowingByPassenger.toEntityFollowingByPassengerList(
                    itinerary.followingByPassengerList
                ),
                locomotiveDataList = EntityLocomotiveData.toEntityLocomotiveDataList(itinerary.locomotiveDataList),
                trainDataList = EntityTrainData.toEntityTrainDataList(itinerary.trainDataList)
            )

        fun toItineraryList(entityItineraryList: MutableList<EntityItinerary>): MutableList<Itinerary?> {
            val list = mutableListOf<Itinerary?>()
            for (item in entityItineraryList) {
                list.add(item.toItinerary())
            }
            return list
        }
    }

    fun toItinerary(): Itinerary = Itinerary(
        itineraryID = itineraryID,
        number = number,
        appearanceAtWork = appearanceAtWork,
        endOfWork = endOfWork,
        restAtThePointOfTurnover = restAtThePointOfTurnover,
        notes = notes,
        followingByPassengerList = EntityFollowingByPassenger.toFollowingByPassengerList(
            followingByPassengerList
        ),
        locomotiveDataList = EntityLocomotiveData.toLocomotiveDataList(locomotiveDataList),
        trainDataList = EntityTrainData.toTrainDataList(trainDataList)
    )
}