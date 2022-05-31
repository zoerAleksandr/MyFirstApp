package com.example.myfirstapp.ui.add_passenger_screen

import androidx.lifecycle.ViewModel
import com.example.myfirstapp.domain.entity.FollowingByPassenger
import com.example.myfirstapp.domain.usecase.passenger.ChangePassengerUseCase
import com.example.myfirstapp.domain.usecase.passenger.GetPassengerByIdUseCase
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

class AddPassengerViewModel : ViewModel(), KoinComponent {
    private val compositeDisposable = CompositeDisposable()

    private val getPassengerByIdUseCase: GetPassengerByIdUseCase by inject()
    private val changePassengerUseCase: ChangePassengerUseCase by inject()

    fun saveNotes(passengerId: String, notes: String?){
        compositeDisposable.add(
            getPassenger(passengerId)
                .subscribeBy(
                    onSuccess = { passenger ->
                        passenger.notes = notes
                        changePassenger(passenger)
                    }
                )
        )
    }

    fun saveDateArrival(passengerId: String, date: Calendar?){
        compositeDisposable.add(
            getPassenger(passengerId)
                .subscribeBy(
                    onSuccess = { passenger ->
                        passenger.arrivalTime = date
                        changePassenger(passenger)
                    }
                )
        )
    }

    fun saveDateDeparture(passengerId: String, date: Calendar?){
        compositeDisposable.add(
            getPassenger(passengerId)
                .subscribeBy(
                    onSuccess = { passenger ->
                        passenger.departureTime = date
                        changePassenger(passenger)
                    }
                )
        )
    }

    fun saveStationArrival(passengerId: String, station: String?){
        compositeDisposable.add(
            getPassenger(passengerId)
                .subscribeBy(
                    onSuccess = { passenger ->
                        passenger.arrivalStation = station
                        changePassenger(passenger)
                    }
                )
        )
    }

    fun saveStationDeparture(passengerId: String, station: String?){
        compositeDisposable.add(
            getPassenger(passengerId)
                .subscribeBy(
                    onSuccess = { passenger ->
                        passenger.departureStation = station
                        changePassenger(passenger)
                    }
                )
        )
    }

    fun saveNumberTrain(passengerId: String, number: String?) {
        compositeDisposable.add(
            getPassenger(passengerId)
                .subscribeBy(
                    onSuccess = { passenger ->
                        passenger.numberOfTrain = number
                        changePassenger(passenger)
                    }
                )
        )
    }

    private fun getPassenger(passengerId: String): Single<FollowingByPassenger> {
        return Single.just(passengerId)
            .observeOn(Schedulers.io())
            .concatMap {
                getPassengerByIdUseCase.execute(passengerId)
            }
    }

    private fun changePassenger(passenger: FollowingByPassenger) {
        compositeDisposable.add(
            Single.just(passenger)
                .observeOn(Schedulers.io())
                .concatMap {
                    changePassengerUseCase.execute(it)
                }
                .subscribe()
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}