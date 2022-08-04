package com.example.myfirstapp.ui.add_itinerary_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirstapp.domain.entity.Itinerary
import com.example.myfirstapp.domain.entity.LocomotiveData
import com.example.myfirstapp.domain.entity.TrainData
import com.example.myfirstapp.domain.usecase.itinerary.GetItineraryByIdUseCase
import com.example.myfirstapp.domain.usecase.itinerary.UpdateItineraryUseCase
import com.example.myfirstapp.domain.usecase.locomotive.AddLocomotiveDataUseCase
import com.example.myfirstapp.domain.usecase.locomotive.GetListLocomotiveByItineraryId
import com.example.myfirstapp.domain.usecase.passenger.AddPassengerUseCase
import com.example.myfirstapp.domain.usecase.train.AddTrainDataUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AddItineraryViewModel : ViewModel(), KoinComponent {
    private val addLocomotiveDataUseCase: AddLocomotiveDataUseCase by inject()
    private val updateItineraryUseCase: UpdateItineraryUseCase by inject()
    private val getItineraryByIdUseCase: GetItineraryByIdUseCase by inject()
    private val addTrainDataUseCase: AddTrainDataUseCase by inject()
    private val addPassengerUseCase: AddPassengerUseCase by inject()
    private val getListLocomotiveByItineraryId: GetListLocomotiveByItineraryId by inject()

    val liveData: MutableLiveData<ListState> = MutableLiveData()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        liveData.postValue(ListState.Error(throwable))
    }

    fun getListLoco(itineraryId: String) {
        viewModelScope.launch(exceptionHandler) {
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
        viewModelScope.launch(exceptionHandler) {
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
        viewModelScope.launch(exceptionHandler) {
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
        viewModelScope.launch(exceptionHandler) {
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
        viewModelScope.launch(exceptionHandler) {
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
}