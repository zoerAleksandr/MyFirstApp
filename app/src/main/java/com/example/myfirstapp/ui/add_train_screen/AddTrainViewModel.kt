package com.example.myfirstapp.ui.add_train_screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfirstapp.domain.entity.Station
import com.example.myfirstapp.domain.entity.TrainData
import com.example.myfirstapp.domain.usecase.station.AddStationUseCase
import com.example.myfirstapp.domain.usecase.station.GetStationUseCase
import com.example.myfirstapp.domain.usecase.station.UpdateStationUseCase
import com.example.myfirstapp.domain.usecase.train.AddTrainDataUseCase
import com.example.myfirstapp.domain.usecase.train.GetTrainDataUseCase
import com.example.myfirstapp.domain.usecase.train.UpdateTrainDataUseCase
import com.example.myfirstapp.utils.AddTrainState
import kotlinx.coroutines.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AddTrainViewModel : ViewModel(), KoinComponent {
    private val addStationLifeData: MutableLiveData<AddTrainState> = MutableLiveData()
    private val changeStationLifeData: MutableLiveData<AddTrainState> = MutableLiveData()
    private val liveData: MutableLiveData<AddTrainState> = MutableLiveData()

    private val addTrainDataUseCase: AddTrainDataUseCase by inject()
    private val getTrainDataUseCase: GetTrainDataUseCase by inject()
    private val updateTrainDataUseCase: UpdateTrainDataUseCase by inject()
    private val addStationUseCase: AddStationUseCase by inject()
    private val updateStationUseCase: UpdateStationUseCase by inject()
    private val getStationUseCase: GetStationUseCase by inject()

    private val scope = CoroutineScope(
        Dispatchers.IO + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            liveData.postValue(AddTrainState.Error(throwable))
        }
    )

    fun newStationObserve(): LiveData<AddTrainState> {
        return addStationLifeData
    }

    fun changeStationObserve(): LiveData<AddTrainState> {
        return changeStationLifeData
    }

    fun saveTrainData(trainData: TrainData) {
        scope.launch {
            // delay для того чтобы успел сработать метод сохранения маршрута
            delay(100)
            kotlin.runCatching { addTrainDataUseCase.execute(trainData) }
                .onSuccess {
                    Log.d("Debug onSuccess", it.toString())
                }
                .onFailure {
                    Log.d("Debug onFailure", it.message.toString())
                }
        }
    }

    private fun getTrainData(trainDataId: String) {
        scope.launch {
            kotlin.runCatching { getTrainDataUseCase.execute(trainDataId) }
                .onSuccess {
                    //TODO
                }
                .onFailure {
                    //TODO
                }
        }
    }

    private fun updateTrainData(trainData: TrainData) {
        scope.launch {
            kotlin.runCatching { updateTrainDataUseCase.execute(trainData) }
                .onSuccess {
                    //TODO
                }
                .onFailure {
                    //TODO
                }
        }
    }

    fun saveStation(station: Station) {
        scope.launch {
            kotlin.runCatching { addStationUseCase.execute(station) }
                .onSuccess {
                    addStationLifeData.postValue(AddTrainState.Success(station))
                }
                .onFailure {
                    addStationLifeData.postValue(AddTrainState.Error(it))
                }
        }

    }

    private fun getStation(stationId: String) {
        scope.launch {
            kotlin.runCatching { getStationUseCase.execute(stationId) }
                .onSuccess {
                    //TODO
                }
                .onFailure {
                    //TODO
                }
        }
    }

    private fun changeStation(station: Station) {
        scope.launch {
            kotlin.runCatching { updateStationUseCase.execute(station) }
                .onSuccess {
                    changeStationLifeData.postValue(AddTrainState.Success(station))
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