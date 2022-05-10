package com.example.myfirstapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myfirstapp.data.room.entity.*
import com.example.myfirstapp.utils.convert_room.*

@Database(
    entities = [
        ItineraryRoomEntity::class,
        FollowingByPassengerRoomEntity::class,
        LocomotiveDataRoomEntity::class,
        TrainDataRoomEntity::class,
        StationRoomEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    ConverterListStrings::class,
    ConvertersItinerary::class,
    ConvertersFollowingByPassenger::class,
    ConvertersLocomotiveData::class,
    ConvertersTrainData::class,
    ConverterTypeOfTraction::class,
    ConverterCountSection::class,
    ConverterDieselFuelSection::class,
    ConverterCounterElectricSection::class,
    ConverterStation::class
)
abstract class ItineraryDataBase : RoomDatabase() {
    companion object {
        fun newInstance() = ItineraryDataBase
    }

    abstract fun itineraryDAO(): ItineraryDAO
}