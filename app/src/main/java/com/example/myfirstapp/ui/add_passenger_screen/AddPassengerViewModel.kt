package com.example.myfirstapp.ui.add_passenger_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfirstapp.domain.entity.Passenger
import com.example.myfirstapp.domain.usecase.passenger.AddPassengerUseCase
import com.example.myfirstapp.domain.usecase.passenger.GetPassengerUseCase
import com.example.myfirstapp.domain.usecase.passenger.UpdatePassengerUseCase
import com.example.myfirstapp.utils.AddPassengerState
import kotlinx.coroutines.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AddPassengerViewModel : ViewModel(), KoinComponent {
    private val addPassengerUseCase: AddPassengerUseCase by inject()
    private val getPassengerUseCase: GetPassengerUseCase by inject()
    private val updatePassengerUseCase: UpdatePassengerUseCase by inject()
    private val liveData: MutableLiveData<AddPassengerState> = MutableLiveData()

    private val scope = CoroutineScope(
        Dispatchers.IO + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            liveData.postValue(AddPassengerState.Error(throwable))
        }
    )

    fun savePassenger(passenger: Passenger) {
        scope.launch {
            kotlin.runCatching { addPassengerUseCase.execute(passenger) }
                .onSuccess {
                    liveData.postValue(AddPassengerState.Success(passenger))
                }
                .onFailure {
                    liveData.postValue(AddPassengerState.Error(it))
                }
        }
    }

    private fun getPassenger(passengerId: String) {
        scope.launch {
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
        scope.launch {
            kotlin.runCatching { updatePassengerUseCase.execute(passenger) }
                .onSuccess {
                    //TODO
                }
                .onFailure {
                    //TODO
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.coroutineContext.cancelChildren()
    }
}