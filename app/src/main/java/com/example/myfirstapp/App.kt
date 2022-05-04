package com.example.myfirstapp

import android.app.Application
import androidx.room.Room
import com.example.myfirstapp.data.room.ItineraryDAO
import com.example.myfirstapp.data.room.ItineraryDataBase

class App : Application() {
    companion object {
        private var appInstance: App? = null
        private var itineraryDataBase: ItineraryDataBase? = null
        private const val DB_ITINERARY_NAME = "Itinerary.db"

        fun getItineraryDAO(): ItineraryDAO {
            if (itineraryDataBase == null) {
                synchronized(ItineraryDataBase::class.java) {
                    if (itineraryDataBase == null) {
                        if (appInstance == null) throw IllegalStateException("Application is null while creating DataBase")
                        itineraryDataBase = Room.databaseBuilder(
                            appInstance!!.applicationContext,
                            ItineraryDataBase::class.java,
                            DB_ITINERARY_NAME
                        ).allowMainThreadQueries().build()
                    }
                }
            }
            return itineraryDataBase!!.itineraryDAO()
        }
    }

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }
}