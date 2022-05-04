package com.example.myfirstapp.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.myfirstapp.domain.Station
import com.google.gson.Gson

@Entity(
    tableName = "station"
)
data class EntityStation(
    @PrimaryKey(autoGenerate = true)
    val stationID: Long,
    val itineraryID: Long,
    val trainDataID: Long,
    var stationName: String?,
    var arrivalTime: String?, // Calendar
    var departureTime: String? // Calendar
) {
    companion object {
        fun createNewEntityStation(station: Station) =
            EntityStation(
                0,
                itineraryID = station.itineraryID,
                trainDataID = station.trainDataID,
                stationName = station.stationName,
                arrivalTime = station.arrivalTime,
                departureTime = station.departureTime,
            )

        fun toEntityStationList(stationList: MutableList<Station?>): MutableList<EntityStation?> {
            val list = mutableListOf<EntityStation?>()
            for (item in stationList) {
                list.add(item?.let { createNewEntityStation(it) })
            }
            return list
        }


        fun toStationList(stationList: MutableList<EntityStation?>): MutableList<Station?> {
            val list = mutableListOf<Station?>()
            for (item in stationList) {
                if (item != null) {
                    list.add(item.toStation())
                }
            }
            return list
        }
    }

    fun toStation(): Station = Station(
        0,
        itineraryID = itineraryID,
        trainDataID = trainDataID,
        stationName = stationName,
        arrivalTime = arrivalTime,
        departureTime = departureTime
    )
}

class ConverterStation {
    @TypeConverter
    fun listToJson(value: List<EntityStation>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Array<EntityStation>::class.java).toList()
}
