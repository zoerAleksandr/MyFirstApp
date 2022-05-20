package com.example.myfirstapp.ui.add_loco_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myfirstapp.domain.usecase.locomotive.AddLocomotiveDataUseCase
import com.example.myfirstapp.domain.usecase.section.diesel.*

class AddLocoViewModelFactory(
    val locomotiveDataID: String,
    private val getListDieselFuelSectionUseCase: GetListDieselFuelSectionUseCase,
    private val getDieselFuelSectionUseCase: GetDieselFuelSectionUseCase,
    private val updateAcceptedDieselFuelSectionUseCase: UpdateAcceptedDieselFuelSectionUseCase,
    private val updateDeliveryDieselFuelSectionUseCase: UpdateDeliveryDieselFuelSectionUseCase,
    private val updateConsumptionDieselFuelUseCase: UpdateConsumptionDieselFuelUseCase,
    private val addDieselFuelSectionUseCase: AddDieselFuelSectionUseCase,
    private val addLocomotiveDataUseCase: AddLocomotiveDataUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddLocoViewModel(
            locomotiveDataID = locomotiveDataID,
            getListDieselFuelSectionUseCase = getListDieselFuelSectionUseCase,
            getDieselFuelSectionUseCase = getDieselFuelSectionUseCase,
            updateAcceptedDieselFuelSectionUseCase = updateAcceptedDieselFuelSectionUseCase,
            updateDeliveryDieselFuelSectionUseCase = updateDeliveryDieselFuelSectionUseCase,
            updateConsumptionDieselFuelUseCase = updateConsumptionDieselFuelUseCase,
            addDieselFuelSectionUseCase = addDieselFuelSectionUseCase,
            addLocomotiveDataUseCase = addLocomotiveDataUseCase
        ) as T
    }
}