package com.example.myfirstapp.ui.add_itinerary_screen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirstapp.domain.entity.Itinerary
import com.example.myfirstapp.domain.entity.LocomotiveData
import com.example.myfirstapp.domain.entity.TrainData
import com.example.myfirstapp.domain.usecase.itinerary.AddItineraryUseCase
import com.example.myfirstapp.domain.usecase.itinerary.GetItineraryByIdUseCase
import com.example.myfirstapp.domain.usecase.itinerary.UpdateItineraryUseCase
import com.example.myfirstapp.domain.usecase.locomotive.AddLocomotiveDataUseCase
import com.example.myfirstapp.domain.usecase.locomotive.GetListLocomotiveByItineraryId
import com.example.myfirstapp.domain.usecase.passenger.AddPassengerUseCase
import com.example.myfirstapp.domain.usecase.passenger.GetPassengerListByItineraryIdUseCase
import com.example.myfirstapp.domain.usecase.train.AddTrainDataUseCase
import kotlinx.coroutines.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AddItineraryViewModel : ViewModel(), KoinComponent {
    private val addItineraryUseCase: AddItineraryUseCase by inject()
    private val addLocomotiveDataUseCase: AddLocomotiveDataUseCase by inject()
    private val updateItineraryUseCase: UpdateItineraryUseCase by inject()
    private val getItineraryByIdUseCase: GetItineraryByIdUseCase by inject()
    private val addTrainDataUseCase: AddTrainDataUseCase by inject()
    private val addPassengerUseCase: AddPassengerUseCase by inject()
    private val getListLocomotiveByItineraryId: GetListLocomotiveByItineraryId by inject()
    private val getPassengerListUseCase: GetPassengerListByItineraryIdUseCase by inject()

    val liveData: MutableLiveData<ListState> = MutableLiveData()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        liveData.postValue(ListState.Error(throwable))
    }

    private val scope = CoroutineScope(
        Dispatchers.IO + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            liveData.postValue(ListState.Error(throwable))
        }
    )

    fun saveItinerary(itinerary: Itinerary) {
        scope.launch {
            kotlin.runCatching { addItineraryUseCase.execute(itinerary) }
                .onSuccess {
                    // TODO Single event complete add itinerary
                }
                .onFailure {
                    // TODO Single event not complete add itinerary
                }
        }
    }

    fun getListLoco(itineraryId: String) {
        scope.launch(exceptionHandler) {
            kotlin.runCatching { getListLocomotiveByItineraryId.execute(itineraryId) }
                .onSuccess { list ->
                    if (list.isEmpty()) {
                        liveData.postValue(ListState.Empty)
                    } else {
                        liveData.postValue(ListState.Success(list))
                    }
                }
                .onFailure { liveData.postValue(ListState.Error(it)) }
        }
    }

    fun addLocomotiveData(locomotiveData: LocomotiveData) {
        scope.launch(exceptionHandler) {
            kotlin.runCatching { addLocomotiveDataUseCase.execute(locomotiveData) }
                .onSuccess {
                    // TODO
                }
                .onFailure {
                    // TODO
                }
        }
    }

    fun addTrainData(itineraryId: String, trainData: TrainData) {
        scope.launch(exceptionHandler) {
            kotlin.runCatching { addTrainDataUseCase.execute(trainData) }
                .onSuccess {
                    // TODO
                }
                .onFailure {
                    // TODO
                }
        }
    }

    private fun changeItinerary(itinerary: Itinerary) {
        scope.launch(exceptionHandler) {
            kotlin.runCatching { updateItineraryUseCase.execute(itinerary) }
                .onSuccess {
                    // TODO
                }
                .onFailure {
                    // TODO
                }
        }
    }

    private fun getItinerary(itineraryId: String) {
        scope.launch(exceptionHandler) {
            kotlin.runCatching {
                updateItineraryUseCase.execute(
                    getItineraryByIdUseCase.execute(
                        itineraryId
                    )
                )
            }
                .onSuccess {
                    // TODO
                }
                .onFailure {
                    // TODO
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.coroutineContext.cancelChildren()
    }
}