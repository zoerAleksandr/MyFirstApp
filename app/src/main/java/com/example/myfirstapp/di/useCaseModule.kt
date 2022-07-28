package com.example.myfirstapp.di

import com.example.myfirstapp.domain.usecase.itinerary.*
import com.example.myfirstapp.domain.usecase.locomotive.AddLocomotiveDataUseCase
import com.example.myfirstapp.domain.usecase.locomotive.ChangeLocomotiveDataUseCase
import com.example.myfirstapp.domain.usecase.locomotive.GetListLocomotiveByItineraryId
import com.example.myfirstapp.domain.usecase.passenger.AddPassengerUseCase
import com.example.myfirstapp.domain.usecase.passenger.ChangePassengerUseCase
import com.example.myfirstapp.domain.usecase.passenger.GetPassengerByIdUseCase
import com.example.myfirstapp.domain.usecase.passenger.RemovePassengerUseCase
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

    // LocomotiveData
    single { AddLocomotiveDataUseCase(repository = get()) }
    single { ChangeLocomotiveDataUseCase(repository = get()) }
    single { GetListLocomotiveByItineraryId(repository = get()) }

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

    // FallowingByPassenger
    single { AddPassengerUseCase(repository = get()) }
    single { GetPassengerByIdUseCase(repository = get()) }
    single { RemovePassengerUseCase(repository = get()) }
    single { ChangePassengerUseCase(repository = get()) }
}