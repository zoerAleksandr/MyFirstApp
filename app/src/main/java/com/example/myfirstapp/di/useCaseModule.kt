package com.example.myfirstapp.di

import com.example.myfirstapp.domain.usecase.itinerary.AddItineraryUseCase
import com.example.myfirstapp.domain.usecase.itinerary.ChangeItineraryUseCase
import com.example.myfirstapp.domain.usecase.itinerary.GetItineraryListByMonth
import com.example.myfirstapp.domain.usecase.itinerary.RemoveItineraryUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetItineraryListByMonth(repository = get()) }
    single { AddItineraryUseCase(repository = get()) }
    single { RemoveItineraryUseCase(repository = get())}
    single { ChangeItineraryUseCase(repository = get()) }
}