package com.example.myfirstapp.ui.add_loco_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myfirstapp.domain.usecase.locomotive.AddLocomotiveDataUseCase
import com.example.myfirstapp.domain.usecase.section.diesel.*

class AddLocoViewModelFactory(
    val locomotiveDataID: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddLocoViewModel(
            locomotiveDataID = locomotiveDataID,
        ) as T
    }
}