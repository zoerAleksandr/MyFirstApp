package com.example.myfirstapp.ui.add_itinerary_screen

import androidx.lifecycle.ViewModel
import com.example.myfirstapp.domain.entity.*
import com.example.myfirstapp.domain.usecase.itinerary.ChangeItineraryUseCase
import com.example.myfirstapp.domain.usecase.itinerary.GetItineraryByIdUseCase
import com.example.myfirstapp.domain.usecase.locomotive.AddLocomotiveDataUseCase
import com.example.myfirstapp.domain.usecase.passenger.AddPassengerUseCase
import com.example.myfirstapp.domain.usecase.section.diesel.AddDieselFuelSectionUseCase
import com.example.myfirstapp.domain.usecase.section.electric.AddElectricSectionUseCase
import com.example.myfirstapp.domain.usecase.train.AddTrainDataUseCase
import io.reactivex.rxjava3.annotations.SchedulerSupport
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AddItineraryViewModel(
    private val addLocomotiveDataUseCase: AddLocomotiveDataUseCase,
    private val changeItineraryUseCase: ChangeItineraryUseCase,
    private val getItineraryByIdUseCase: GetItineraryByIdUseCase,
    private val addTrainDataUseCase: AddTrainDataUseCase,
    private val addDieselFuelSectionUseCase: AddDieselFuelSectionUseCase,
    private val addElectricSectionUseCase: AddElectricSectionUseCase
) : ViewModel(), KoinComponent {
    private val compositeDisposable = CompositeDisposable()
    private val addPassengerUseCase: AddPassengerUseCase by inject()

    fun addPassengerData(passenger: FollowingByPassenger) {
        compositeDisposable.add(
            Single.just(passenger)
                .observeOn(Schedulers.io())
                .concatMap {
                    addPassengerUseCase.execute(passenger)
                }
                .subscribe()
        )
    }

    fun addLocomotiveData(locomotiveData: LocomotiveData) {
        compositeDisposable.add(
            Single.just(locomotiveData)
                .observeOn(Schedulers.io())
                .concatMap {
                    addLocomotiveDataUseCase.execute(it)
                }
                .subscribe()
        )
    }

    fun addTrainData(itineraryId: String, trainData: TrainData) {
        compositeDisposable.add(
            Single.just(trainData)
                .observeOn(Schedulers.io())
                .concatMap {
                    addTrainDataUseCase.execute(it)
                }
                .subscribeBy(
                    onSuccess = {
                        saveTrainData(itineraryId, trainData)
                    }
                )
        )
    }

    private fun saveTrainData(itineraryId: String, trainData: TrainData) {
        compositeDisposable.add(
            getItinerary(itineraryId)
                .observeOn(Schedulers.io())
                .subscribeBy(
                    onSuccess = { itinerary ->
                        val list = mutableListOf<TrainData>()
                        list.addAll(itinerary.trainDataList)
                        list.add(trainData)
                        itinerary.trainDataList = list
                        changeItinerary(itinerary)
                    }
                )
        )
    }

    private fun changeItinerary(itinerary: Itinerary) {
        Single.just(itinerary)
            .observeOn(Schedulers.io())
            .concatMap {
                changeItineraryUseCase.execute(itinerary)
            }
            .subscribe()
    }

    private fun getItinerary(itineraryId: String): Single<Itinerary> {
        return Single.just(itineraryId)
            .observeOn(Schedulers.io())
            .concatMap {
                getItineraryByIdUseCase.execute(it)
            }
    }

    fun addDieselFuelSection(dieselFuelSection: DieselFuelSection) {
        compositeDisposable.add(
            Single.just(dieselFuelSection)
                .observeOn(Schedulers.io())
                .concatMap {
                    addDieselFuelSectionUseCase.execute(it)
                }
                .subscribe()
        )
    }

    fun addElectricSection(electricSection: ElectricSection) {
        compositeDisposable.add(
            Single.just(electricSection)
                .observeOn(Schedulers.io())
                .concatMap {
                    addElectricSectionUseCase.execute(it)
                }
                .subscribe()
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}