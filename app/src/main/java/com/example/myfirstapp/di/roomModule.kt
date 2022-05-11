package com.example.myfirstapp.di

import androidx.room.Room
import com.example.myfirstapp.data.room.ItineraryDataBase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val DB_ITINERARY_NAME = "Itinerary.db"
val roomModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            ItineraryDataBase::class.java,
            DB_ITINERARY_NAME
        ).build()
    }

    single { get<ItineraryDataBase>().itineraryDAO() }
}