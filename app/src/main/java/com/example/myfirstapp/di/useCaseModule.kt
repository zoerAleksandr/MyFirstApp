package com.example.myfirstapp.di

import com.example.myfirstapp.domain.usecase.itinerary.AddItineraryUseCase
import com.example.myfirstapp.domain.usecase.itinerary.ChangeItineraryUseCase
import com.example.myfirstapp.domain.usecase.itinerary.GetItineraryListByMonth
import com.example.myfirstapp.domain.usecase.itinerary.RemoveItineraryUseCase
import com.example.myfirstapp.domain.usecase.locomotive.*
import com.example.myfirstapp.domain.usecase.section.diesel.*
import com.example.myfirstapp.domain.usecase.section.electric.*
import org.koin.dsl.module

val useCaseModule = module {
    single { GetItineraryListByMonth(repository = get()) }
    single { AddItineraryUseCase(repository = get()) }
    single { RemoveItineraryUseCase(repository = get()) }
    single { ChangeItineraryUseCase(repository = get()) }

    // LocomotiveData
    single { AddLocomotiveDataUseCase(repository = get()) }
    single { UpdateSeriesLocoUseCase(repository = get()) }
    single { UpdateNumberLocoUseCase(repository = get()) }
    single { UpdateTypeOfTractionUseCase(repository = get()) }
    single { UpdateCountSectionUseCase(repository = get()) }

    // Diesel Section
    single { AddDieselFuelSectionUseCase(repository = get()) }
    single { GetListDieselFuelSectionUseCase(repository = get()) }
    single { UpdateAcceptedDieselFuelSectionUseCase(repository = get()) }
    single { UpdateDeliveryDieselFuelSectionUseCase(repository = get()) }
    single { UpdateConsumptionDieselFuelUseCase(repository = get()) }
    single { GetDieselFuelSectionUseCase(repository = get()) }

    // Electric Section
    single { AddElectricSectionUseCase(repository = get()) }
    single { ChangeElectricSectionUseCase(repository = get()) }
    single { GetElectricSectionUseCase(repository = get()) }
    single { GetElectricSectionUseCaseList(repository = get()) }
    single { RemoveElectricSectionUseCase(repository = get()) }
    single { UpdateAcceptedEnergyElectricSectionUseCase(repository = get()) }
    single { UpdateDeliveryEnergyElectricSectionUseCase(repository = get()) }
    single { UpdateConsumptionEnergyElectricSectionUseCase(repository = get()) }
    single { UpdateAcceptedRecoveryElectricSectionUseCase(repository = get()) }
    single { UpdateDeliveryRecoveryElectricSectionUseCase(repository = get()) }
    single { UpdateConsumptionRecoveryElectricSectionUseCase(repository = get()) }
}