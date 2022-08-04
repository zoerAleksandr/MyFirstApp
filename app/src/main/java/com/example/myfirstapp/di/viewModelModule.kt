package com.example.myfirstapp.di

import com.example.myfirstapp.ui.add_itinerary_screen.AddItineraryViewModel
import com.example.myfirstapp.ui.add_passenger_screen.AddPassengerViewModel
import com.example.myfirstapp.ui.add_train_screen.AddTrainViewModel
import com.example.myfirstapp.ui.main_screen.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MainViewModel()
    }

    viewModel {
        AddItineraryViewModel()
    }

    viewModel {
        AddTrainViewModel()
    }

    viewModel {
        AddPassengerViewModel()
    }
}