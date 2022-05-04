package com.example.myfirstapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        EntityItinerary::class,
        EntityFollowingByPassenger::class,
        EntityLocomotiveData::class,
        EntityTrainData::class,
        EntityStation::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
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