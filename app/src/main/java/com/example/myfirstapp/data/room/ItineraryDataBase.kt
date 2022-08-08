package com.example.myfirstapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myfirstapp.data.room.entity.*
import com.example.myfirstapp.utils.convert_room.*

@Database(
    entities = [
        ItineraryRoomEntity::class,
        PassengerRoomEntity::class,
        LocomotiveDataRoomEntity::class,
        TrainDataRoomEntity::class,
        StationRoomEntity::class,
        DieselFuelSectionRoomEntity::class,
        ElectricSectionRoomEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    ConverterCalendar::class,
    ConverterListStrings::class,
    ConvertersItinerary::class,
    ConvertersFollowingByPassenger::class,
    ConvertersLocomotiveData::class,
    ConvertersTrainData::class,
    ConverterTypeOfTraction::class,
    ConverterCountSection::class,
    ConverterDieselFuelSectionList::class,
    ConverterCounterElectricSection::class,
    ConverterStation::class,
    ConvertElectricSectionList::class
)
abstract class ItineraryDataBase : RoomDatabase() {
    abstract fun itineraryDAO(): ItineraryDAO
}