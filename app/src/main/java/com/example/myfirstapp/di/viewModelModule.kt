package com.example.myfirstapp.di

import com.example.myfirstapp.ui.main_screen.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel{
        MainViewModel(getItineraryListUseCase = get())
    }
}