package com.example.myfirstapp.di

import com.example.myfirstapp.domain.usecase.itinerary.AddItineraryUseCase
import com.example.myfirstapp.domain.usecase.itinerary.ChangeItineraryUseCase
import com.example.myfirstapp.domain.usecase.itinerary.GetItineraryListByMonth
import com.example.myfirstapp.domain.usecase.itinerary.RemoveItineraryUseCase
import com.example.myfirstapp.domain.usecase.locomotive.AddLocomotiveDataUseCase
import com.example.myfirstapp.domain.usecase.section.diesel.*
import org.koin.dsl.module

val useCaseModule = module {
    single { GetItineraryListByMonth(repository = get()) }
    single { AddItineraryUseCase(repository = get()) }
    single { RemoveItineraryUseCase(repository = get()) }
    single { ChangeItineraryUseCase(repository = get()) }

    single { AddDieselFuelSectionUseCase(repository = get()) }
    single { GetListDieselFuelSectionUseCase(repository = get()) }
    single { UpdateAcceptedDieselFuelSectionUseCase(repository = get()) }
    single { UpdateDeliveryDieselFuelSectionUseCase(repository = get()) }
    single { AddLocomotiveDataUseCase(repository = get()) }
    single { UpdateConsumptionDieselFuelUseCase(repository = get()) }
    single { GetDieselFuelSectionUseCase(repository = get()) }
}