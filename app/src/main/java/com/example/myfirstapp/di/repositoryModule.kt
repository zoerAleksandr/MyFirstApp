package com.example.myfirstapp.di

import com.example.myfirstapp.data.room.repository.*
import com.example.myfirstapp.domain.repository.*
import org.koin.dsl.module

val repositoryModule = module {
    single<IRepositoryItinerary> { RoomRepositoryItinerary() }
    single<IRepositoryLocomotive> { RoomRepositoryLocomotive() }
    single<IRepositoryPassenger> { RoomRepositoryPassenger() }
    single<IRepositoryTrain> { RoomRepositoryTrain() }
    single<IRepositoryStation> { RoomRepositoryStation() }
}