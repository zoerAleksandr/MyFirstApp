package com.example.myfirstapp.ui.add_itinerary_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfirstapp.domain.entity.*
import com.example.myfirstapp.domain.usecase.itinerary.ChangeItineraryUseCase
import com.example.myfirstapp.domain.usecase.itinerary.GetItineraryByIdUseCase
import com.example.myfirstapp.domain.usecase.locomotive.AddLocomotiveDataUseCase
import com.example.myfirstapp.domain.usecase.locomotive.GetListLocomotiveByItineraryId
import com.example.myfirstapp.domain.usecase.passenger.AddPassengerUseCase
import com.example.myfirstapp.domain.usecase.train.AddTrainDataUseCase
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

class AddItineraryViewModel(
    private val addLocomotiveDataUseCase: AddLocomotiveDataUseCase,
    private val changeItineraryUseCase: ChangeItineraryUseCase,
    private val getItineraryByIdUseCase: GetItineraryByIdUseCase,
    private val addTrainDataUseCase: AddTrainDataUseCase
) : ViewModel(), KoinComponent {
    private val compositeDisposable = CompositeDisposable()
    private val addPassengerUseCase: AddPassengerUseCase by inject()
    private val getListLocomotiveByItineraryId: GetListLocomotiveByItineraryId by inject()
    val listLocoLiveData: MutableLiveData<ListState> = MutableLiveData()

    fun getListLoco(itineraryId: String) {
        compositeDisposable.add(
            Single.just(itineraryId)
                .subscribeOn(Schedulers.io())
                .concatMap {
                    getListLocomotiveByItineraryId.execute(it)
                }
                .subscribeBy(
                    onSuccess = { list ->
                        if (list.isEmpty()) {
                            listLocoLiveData.postValue(ListState.Empty)
                        } else {
                            listLocoLiveData.postValue(ListState.Success(list))
                        }
                    },
                    onError = {
                        listLocoLiveData.postValue(ListState.Error(it))
                    }
                )
        )
    }

    fun saveNotes(itineraryId: String, notes: String) {
        compositeDisposable.add(
            getItinerary(itineraryId)
                .observeOn(Schedulers.io())
                .subscribeBy(
                    onSuccess = {
                        it.notes = notes
                        changeItinerary(it)
                    }
                )
        )
    }

    fun saveRest(itineraryId: String, rest: Boolean) {
        compositeDisposable.add(
            getItinerary(itineraryId)
                .observeOn(Schedulers.io())
                .subscribeBy(
                    onSuccess = {
                        it.restAtThePointOfTurnover = rest
                        changeItinerary(it)
                    }
                )
        )
    }

    fun saveCalendarEnding(itineraryId: String, ending: Calendar?) {
        compositeDisposable.add(
            getItinerary(itineraryId)
                .observeOn(Schedulers.io())
                .subscribeBy(
                    onSuccess = {
                        it.endOfWork = ending
                        changeItinerary(it)
                    }
                )
        )
    }

    fun saveCalendarTurnout(itineraryId: String, turnout: Calendar?) {
        compositeDisposable.add(
            getItinerary(itineraryId)
                .observeOn(Schedulers.io())
                .subscribeBy(
                    onSuccess = {
                        it.appearanceAtWork = turnout
                        changeItinerary(it)
                    }
                )
        )
    }

    fun saveNumberItinerary(itineraryId: String, number: String) {
        compositeDisposable.add(
            getItinerary(itineraryId)
                .observeOn(Schedulers.io())
                .subscribeBy(
                    onSuccess = {
                        it.number = number
                        changeItinerary(it)
                    }
                )
        )
    }

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

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}