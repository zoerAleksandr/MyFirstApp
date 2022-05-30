package com.example.myfirstapp.ui.add_train_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfirstapp.domain.entity.Station
import com.example.myfirstapp.domain.entity.TrainData
import com.example.myfirstapp.domain.usecase.station.AddStationUseCase
import com.example.myfirstapp.domain.usecase.station.ChangeStationUseCase
import com.example.myfirstapp.domain.usecase.station.GetStationUseCase
import com.example.myfirstapp.domain.usecase.train.ChangeTrainDataUseCase
import com.example.myfirstapp.domain.usecase.train.GetTrainDataByIdUseCase
import com.example.myfirstapp.utils.AddTrainState
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

class AddTrainViewModel : ViewModel(), KoinComponent {
    private val compositeDisposable = CompositeDisposable()
    private val addStationLifeData: MutableLiveData<AddTrainState> = MutableLiveData()
    private val changeStationLifeData: MutableLiveData<AddTrainState> = MutableLiveData()

    private val getTrainDataUseCase: GetTrainDataByIdUseCase by inject()
    private val changeTrainDataUseCase: ChangeTrainDataUseCase by inject()
    private val addStationUseCase: AddStationUseCase by inject()
    private val changeStationUseCase: ChangeStationUseCase by inject()
    private val getStationUseCase: GetStationUseCase by inject()

    fun newStationObserve(): LiveData<AddTrainState> {
        return addStationLifeData
    }

    fun changeStationObserve(): LiveData<AddTrainState> {
        return changeStationLifeData
    }

    fun saveTimeArrival(stationId: String, arrival: Calendar) {
        compositeDisposable.add(
            getStation(stationId)
                .subscribeBy(
                    onSuccess = { station ->
                        station.arrivalTime = arrival
                        changeStation(station)
                    }
                )
        )
    }

    fun saveTimeDeparture(stationId: String, departure: Calendar) {
        compositeDisposable.add(
            getStation(stationId)
                .subscribeBy(
                    onSuccess = { station ->
                        station.departureTime = departure
                        changeStation(station)
                    }
                )
        )
    }

    fun saveStationName(stationId: String, stationName: String?){
        compositeDisposable.add(
            getStation(stationId)
                .subscribeBy(
                    onSuccess = { station ->
                        station.stationName = stationName
                        changeStation(station)
                    }
                )
        )
    }

    private fun changeStation(station: Station) {
        Single.just(station)
            .observeOn(Schedulers.io())
            .concatMap {
                changeStationUseCase.execute(station)
            }
            .subscribeBy(
                onSuccess = {
                    changeStationLifeData.postValue(AddTrainState.Success(station))
                }
            )
    }

    private fun getStation(stationId: String): Single<Station> {
        return Single.just(stationId)
            .observeOn(Schedulers.io())
            .concatMap {
                getStationUseCase.execute(stationId)
            }
    }

    fun saveNumberOfTrain(trainDataID: String, number: Int?) {
        compositeDisposable.add(
            getTrainData(trainDataID)
                .subscribeBy(
                    onSuccess = { trainData ->
                        trainData.numberOfTrain = number
                        changeTrainData(trainData)
                    }
                )
        )
    }

    fun saveWeight(trainDataID: String, weight: Int?) {
        compositeDisposable.add(
            getTrainData(trainDataID)
                .subscribeBy(
                    onSuccess = { trainData ->
                        trainData.weight = weight
                        changeTrainData(trainData)
                    }
                )
        )
    }

    fun saveWheelAxle(trainDataID: String, wheelAxle: Int?) {
        compositeDisposable.add(
            getTrainData(trainDataID)
                .subscribeBy(
                    onSuccess = { trainData ->
                        trainData.wheelAxle = wheelAxle
                        changeTrainData(trainData)
                    }
                )
        )
    }

    fun saveConditionalLength(trainDataID: String, conditionalLength: Int?) {
        compositeDisposable.add(
            getTrainData(trainDataID)
                .subscribeBy(
                    onSuccess = { trainData ->
                        trainData.conditionalLength = conditionalLength
                        changeTrainData(trainData)
                    }
                )
        )
    }

    private fun saveStationToTrainData(trainDataID: String, station: Station) {
        compositeDisposable.add(
            getTrainData(trainDataID)
                .subscribeBy(
                    onSuccess = { trainData ->
                        val list = mutableListOf<Station>().apply {
                            addAll(trainData.stations)
                            add(station)
                        }
                        trainData.stations = list
                        changeTrainData(trainData)
                    }
                )
        )
    }

    fun saveStation(trainDataID: String, station: Station) {
        compositeDisposable.add(
            Single.just(trainDataID)
                .observeOn(Schedulers.io())
                .concatMap {
                    addStationUseCase.execute(station)
                }
                .subscribeBy(
                    onSuccess = {
                        addStationLifeData.postValue(AddTrainState.Success(station))
                        saveStationToTrainData(trainDataID, station)
                    }
                )
        )
    }

    private fun getTrainData(trainDataID: String): Single<TrainData> {
        return Single.just(trainDataID)
            .observeOn(Schedulers.io())
            .concatMap {
                getTrainDataUseCase.execute(trainDataID)
            }
    }

    private fun changeTrainData(trainData: TrainData) {
        changeTrainDataUseCase.execute(trainData)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}