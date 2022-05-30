package com.example.myfirstapp.di

import com.example.myfirstapp.domain.usecase.itinerary.*
import com.example.myfirstapp.domain.usecase.locomotive.*
import com.example.myfirstapp.domain.usecase.section.diesel.*
import com.example.myfirstapp.domain.usecase.section.electric.*
import com.example.myfirstapp.domain.usecase.station.*
import com.example.myfirstapp.domain.usecase.train.AddTrainDataUseCase
import com.example.myfirstapp.domain.usecase.train.ChangeTrainDataUseCase
import com.example.myfirstapp.domain.usecase.train.GetTrainDataByIdUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetItineraryListByMonth(repository = get()) }
    single { GetItineraryByIdUseCase(repository = get()) }
    single { AddItineraryUseCase(repository = get()) }
    single { RemoveItineraryUseCase(repository = get()) }
    single { ChangeItineraryUseCase(repository = get()) }
    single { UpdateItineraryLocomotiveUseCase(repository = get()) }

    // LocomotiveData
    single { AddLocomotiveDataUseCase(repository = get()) }
    single { ChangeLocomotiveDataUseCase(repository = get()) }
    single { UpdateSeriesLocoUseCase(repository = get()) }
    single { UpdateNumberLocoUseCase(repository = get()) }
    single { UpdateTypeOfTractionUseCase(repository = get()) }
    single { UpdateCountSectionUseCase(repository = get()) }
    single { UpdateStartAcceptanceUseCase(repository = get()) }
    single { UpdateEndAcceptanceUseCase(repository = get()) }
    single { UpdateStartDeliveryUseCase(repository = get()) }
    single { UpdateEndDeliveryUseCase(repository = get()) }
    single { UpdateBreakShoesUseCase(repository = get()) }
    single { UpdateExtinguishersUseCase(repository = get()) }

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

    // Train Data
    single { AddTrainDataUseCase(repository = get()) }
    single { GetTrainDataByIdUseCase(repository = get()) }
    single { ChangeTrainDataUseCase(repository = get()) }

    // Station
    single { AddStationUseCase(repository = get()) }
    single { GetStationUseCase(repository = get()) }
    single { GetListStationUseCase(repository = get()) }
    single { RemoveStationUseCase(repository = get()) }
    single { ChangeStationUseCase(repository = get()) }
}