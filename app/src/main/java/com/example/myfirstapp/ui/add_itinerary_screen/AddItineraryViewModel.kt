package com.example.myfirstapp.ui.add_itinerary_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.myfirstapp.domain.entity.DieselFuelSection
import com.example.myfirstapp.domain.entity.ElectricSection
import com.example.myfirstapp.domain.entity.Itinerary
import com.example.myfirstapp.domain.entity.LocomotiveData
import com.example.myfirstapp.domain.usecase.itinerary.AddItineraryUseCase
import com.example.myfirstapp.domain.usecase.locomotive.AddLocomotiveDataUseCase
import com.example.myfirstapp.domain.usecase.section.diesel.AddDieselFuelSectionUseCase
import com.example.myfirstapp.domain.usecase.section.electric.AddElectricSectionUseCase
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

class AddItineraryViewModel(
    private val addItineraryUseCase: AddItineraryUseCase,
    private val addLocomotiveDataUseCase: AddLocomotiveDataUseCase,
    private val addDieselFuelSectionUseCase: AddDieselFuelSectionUseCase,
    private val addElectricSectionUseCase: AddElectricSectionUseCase
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    fun addLocomotiveData(locomotiveData: LocomotiveData) {
        compositeDisposable.add(
            Single.just(locomotiveData)
                .observeOn(Schedulers.io())
                .concatMap {
                    addLocomotiveDataUseCase.execute(locomotiveData)
                }
                .subscribe()
        )
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

    fun addElectricSection(electricSection: ElectricSection){
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