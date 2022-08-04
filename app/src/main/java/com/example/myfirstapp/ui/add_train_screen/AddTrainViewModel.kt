package com.example.myfirstapp.ui.add_train_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirstapp.domain.entity.Station
import com.example.myfirstapp.domain.entity.TrainData
import com.example.myfirstapp.domain.usecase.station.AddStationUseCase
import com.example.myfirstapp.domain.usecase.station.GetStationUseCase
import com.example.myfirstapp.domain.usecase.station.UpdateStationUseCase
import com.example.myfirstapp.domain.usecase.train.GetTrainDataUseCase
import com.example.myfirstapp.domain.usecase.train.UpdateTrainDataUseCase
import com.example.myfirstapp.utils.AddTrainState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AddTrainViewModel : ViewModel(), KoinComponent {
    private val addStationLifeData: MutableLiveData<AddTrainState> = MutableLiveData()
    private val changeStationLifeData: MutableLiveData<AddTrainState> = MutableLiveData()

    private val getTrainDataUseCase: GetTrainDataUseCase by inject()
    private val updateTrainDataUseCase: UpdateTrainDataUseCase by inject()
    private val addStationUseCase: AddStationUseCase by inject()
    private val updateStationUseCase: UpdateStationUseCase by inject()
    private val getStationUseCase: GetStationUseCase by inject()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        addStationLifeData.postValue(AddTrainState.Error(throwable))
    }

    fun newStationObserve(): LiveData<AddTrainState> {
        return addStationLifeData
    }

    fun changeStationObserve(): LiveData<AddTrainState> {
        return changeStationLifeData
    }

    // saveTrainData
    private fun saveTrainData(trainData: TrainData) {
        // TODO
    }

    // getTrainData
    private fun getTrainData(trainDataId: String) {
        viewModelScope.launch(exceptionHandler) {
            kotlin.runCatching { getTrainDataUseCase.execute(trainDataId) }
                .onSuccess {
                    //TODO
                }
                .onFailure {
                    //TODO
                }
        }
    }

    // updateTrainData
    private fun updateTrainData(trainData: TrainData) {
        viewModelScope.launch(exceptionHandler) {
            kotlin.runCatching { updateTrainDataUseCase.execute(trainData) }
                .onSuccess {
                    //TODO
                }
                .onFailure {
                    //TODO
                }
        }
    }

    // saveStation()
    fun saveStation(station: Station) {
        viewModelScope.launch(exceptionHandler) {
            kotlin.runCatching { addStationUseCase.execute(station) }
                .onSuccess {
                    addStationLifeData.postValue(AddTrainState.Success(station))
                }
                .onFailure {
                    //TODO
                }
        }

    }

    // getStation()
    private fun getStation(stationId: String) {
        viewModelScope.launch(exceptionHandler) {
            kotlin.runCatching { getStationUseCase.execute(stationId) }
                .onSuccess {
                    //TODO
                }
                .onFailure {
                    //TODO
                }
        }
    }

    // updateStation()
    private fun changeStation(station: Station) {
        viewModelScope.launch(exceptionHandler) {
            kotlin.runCatching { updateStationUseCase.execute(station) }
                .onSuccess {
                    changeStationLifeData.postValue(AddTrainState.Success(station))
                }
                .onFailure {
                    //TODO
                }
        }
    }
}