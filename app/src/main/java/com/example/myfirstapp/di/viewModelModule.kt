package com.example.myfirstapp.di

import com.example.myfirstapp.ui.add_itinerary_screen.AddItineraryViewModel
import com.example.myfirstapp.ui.add_passenger_screen.AddPassengerViewModel
import com.example.myfirstapp.ui.add_train_screen.AddTrainViewModel
import com.example.myfirstapp.ui.main_screen.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MainViewModel(
            getItineraryListUseCase = get(),
            addItineraryUseCase = get()
        )
    }

    viewModel {
        AddItineraryViewModel(
            addLocomotiveDataUseCase = get(),
            changeItineraryUseCase = get(),
            getItineraryByIdUseCase = get(),
            addTrainDataUseCase = get(),
            addDieselFuelSectionUseCase = get(),
            addElectricSectionUseCase = get()
        )
    }

    viewModel {
        AddTrainViewModel()
    }

    viewModel {
        AddPassengerViewModel()
    }
}