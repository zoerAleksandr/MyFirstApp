package com.example.myfirstapp.di

import com.example.myfirstapp.data.room.repository.RoomRepositoryItinerary
import com.example.myfirstapp.domain.repository.IRepositoryItinerary
import org.koin.dsl.module

val repositoryModule = module {
    single<IRepositoryItinerary> { RoomRepositoryItinerary() }
}