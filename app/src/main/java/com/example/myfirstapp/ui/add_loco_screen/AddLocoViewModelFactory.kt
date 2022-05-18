package com.example.myfirstapp.ui.add_loco_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myfirstapp.domain.usecase.locomotive.AddLocomotiveDataUseCase
import com.example.myfirstapp.domain.usecase.section.diesel.AddDieselFuelSectionUseCase
import com.example.myfirstapp.domain.usecase.section.diesel.GetListDieselFuelSectionUseCase
import com.example.myfirstapp.domain.usecase.section.diesel.UpdateAcceptedDieselFuelSectionUseCase
import com.example.myfirstapp.domain.usecase.section.diesel.UpdateDeliveryDieselFuelSectionUseCase

class AddLocoViewModelFactory(
    val locomotiveDataID: String,
    private val getListDieselFuelSectionUseCase: GetListDieselFuelSectionUseCase,
    private val updateAcceptedDieselFuelSectionUseCase: UpdateAcceptedDieselFuelSectionUseCase,
    private val updateDeliveryDieselFuelSectionUseCase: UpdateDeliveryDieselFuelSectionUseCase,
    private val addDieselFuelSectionUseCase: AddDieselFuelSectionUseCase,
    private val addLocomotiveDataUseCase: AddLocomotiveDataUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddLocoViewModel(
            locomotiveDataID = locomotiveDataID,
            getListDieselFuelSectionUseCase = getListDieselFuelSectionUseCase,
            updateAcceptedDieselFuelSectionUseCase = updateAcceptedDieselFuelSectionUseCase,
            updateDeliveryDieselFuelSectionUseCase = updateDeliveryDieselFuelSectionUseCase,
            addDieselFuelSectionUseCase = addDieselFuelSectionUseCase,
            addLocomotiveDataUseCase = addLocomotiveDataUseCase
        ) as T
    }
}