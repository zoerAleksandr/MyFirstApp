package com.example.myfirstapp.di

import com.example.myfirstapp.domain.usecase.itinerary.GetItineraryListByMonth
import org.koin.dsl.module

val useCaseModule = module {
    single { GetItineraryListByMonth(repository = get()) }
}