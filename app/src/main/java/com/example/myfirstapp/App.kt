package com.example.myfirstapp

import android.app.Application
import androidx.room.Room
import com.example.myfirstapp.data.room.ItineraryDAO
import com.example.myfirstapp.data.room.ItineraryDataBase
import com.example.myfirstapp.di.repositoryModule
import com.example.myfirstapp.di.roomModule
import com.example.myfirstapp.di.useCaseModule
import com.example.myfirstapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                listOf(
                    roomModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}