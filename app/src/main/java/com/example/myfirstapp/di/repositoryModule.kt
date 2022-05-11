package com.example.myfirstapp.di

import com.example.myfirstapp.data.room.RoomRepository
import com.example.myfirstapp.domain.repository.IRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<IRepository> { RoomRepository() }
}