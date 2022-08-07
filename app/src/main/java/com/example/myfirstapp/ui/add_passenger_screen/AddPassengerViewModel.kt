package com.example.myfirstapp.ui.add_passenger_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirstapp.domain.entity.Passenger
import com.example.myfirstapp.domain.usecase.passenger.GetPassengerUseCase
import com.example.myfirstapp.domain.usecase.passenger.UpdatePassengerUseCase
import com.example.myfirstapp.utils.AddPassengerState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AddPassengerViewModel : ViewModel(), KoinComponent {
    private val getPassengerUseCase: GetPassengerUseCase by inject()
    private val updatePassengerUseCase: UpdatePassengerUseCase by inject()
    private val liveData: MutableLiveData<AddPassengerState> = MutableLiveData()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        liveData.postValue(AddPassengerState.Error(throwable))
    }

    private fun getPassenger(passengerId: String) {
        viewModelScope.launch(exceptionHandler) {
            kotlin.runCatching { getPassengerUseCase.execute(passengerId) }
                .onSuccess {
                    //TODO
                }
                .onFailure {
                    //TODO
                }
        }
    }

    private fun changePassenger(passenger: Passenger) {
        viewModelScope.launch(exceptionHandler) {
            kotlin.runCatching { updatePassengerUseCase.execute(passenger) }
                .onSuccess {
                    //TODO
                }
                .onFailure {
                    //TODO
                }
        }
    }
}