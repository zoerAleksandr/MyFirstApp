package com.example.myfirstapp.di

import com.example.myfirstapp.domain.usecase.itinerary.*
import com.example.myfirstapp.domain.usecase.locomotive.AddLocomotiveDataUseCase
import com.example.myfirstapp.domain.usecase.locomotive.GetListLocomotiveByItineraryId
import com.example.myfirstapp.domain.usecase.locomotive.UpdateLocomotiveDataUseCase
import com.example.myfirstapp.domain.usecase.passenger.AddPassengerUseCase
import com.example.myfirstapp.domain.usecase.passenger.DeletePassengerUseCase
import com.example.myfirstapp.domain.usecase.passenger.GetPassengerUseCase
import com.example.myfirstapp.domain.usecase.passenger.UpdatePassengerUseCase
import com.example.myfirstapp.domain.usecase.station.*
import com.example.myfirstapp.domain.usecase.train.AddTrainDataUseCase
import com.example.myfirstapp.domain.usecase.train.UpdateTrainDataUseCase
import com.example.myfirstapp.domain.usecase.train.GetTrainDataUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetItineraryListByMonth(repository = get()) }
    single { GetItineraryByIdUseCase(repository = get()) }
    single { AddItineraryUseCase(repository = get()) }
    single { DeleteItineraryUseCase(repository = get()) }
    single { UpdateItineraryUseCase(repository = get()) }

    // LocomotiveData
    single { AddLocomotiveDataUseCase(repository = get()) }
    single { UpdateLocomotiveDataUseCase(repository = get()) }
    single { GetListLocomotiveByItineraryId(repository = get()) }

    // Train Data
    single { AddTrainDataUseCase(repository = get()) }
    single { GetTrainDataUseCase(repository = get()) }
    single { UpdateTrainDataUseCase(repository = get()) }

    // Station
    single { AddStationUseCase(repository = get()) }
    single { GetStationUseCase(repository = get()) }
    single { GetListStationUseCase(repository = get()) }
    single { DeleteStationUseCase(repository = get()) }
    single { UpdateStationUseCase(repository = get()) }

    // FallowingByPassenger
    single { AddPassengerUseCase(repository = get()) }
    single { GetPassengerUseCase(repository = get()) }
    single { DeletePassengerUseCase(repository = get()) }
    single { UpdatePassengerUseCase(repository = get()) }
}