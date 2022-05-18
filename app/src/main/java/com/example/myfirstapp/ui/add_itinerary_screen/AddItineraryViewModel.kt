package com.example.myfirstapp.ui.add_itinerary_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.myfirstapp.domain.entity.DieselFuelSection
import com.example.myfirstapp.domain.entity.Itinerary
import com.example.myfirstapp.domain.entity.LocomotiveData
import com.example.myfirstapp.domain.usecase.itinerary.AddItineraryUseCase
import com.example.myfirstapp.domain.usecase.locomotive.AddLocomotiveDataUseCase
import com.example.myfirstapp.domain.usecase.section.diesel.AddDieselFuelSectionUseCase
import com.example.myfirstapp.utils.generateStringID
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

class AddItineraryViewModel(
    private val addItineraryUseCase: AddItineraryUseCase,
    private val addLocomotiveDataUseCase: AddLocomotiveDataUseCase,
    private val addDieselFuelSectionUseCase: AddDieselFuelSectionUseCase,
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    fun saveItinerary(
        itineraryID: String,
        number: String?,
        appearanceAtWork: Calendar?,
        endOfWork: Calendar?,
        restAtThePointOfTurnover: Boolean,
        notes: String?
    ) {
        val itinerary = Itinerary(
            itineraryID,
            number,
            appearanceAtWork,
            endOfWork,
            restAtThePointOfTurnover,
            notes,
            mutableListOf(),
            mutableListOf(),
            mutableListOf()
        )
        compositeDisposable.add(
            Single.just(itinerary)
                .observeOn(Schedulers.io())
                .map {
                    addItineraryUseCase.execute(itinerary)
                }
                .subscribeBy(
                    onSuccess = {
                        Log.d("Debug", "saveItinerary onSuccess - $it")
                    },
                    onError = {
                        Log.d("Debug", "saveItinerary onError - $it")
                    }
                )
        )
    }


    fun addLocomotiveData(locomotiveData: LocomotiveData) {
        Single.just(locomotiveData)
            .observeOn(Schedulers.io())
            .concatMap {
                addLocomotiveDataUseCase.execute(locomotiveData)
            }
            .subscribe()
    }

    fun addDieselFuelSection(dieselFuelSection: DieselFuelSection){
        Single.just(dieselFuelSection)
            .observeOn(Schedulers.io())
            .concatMap {
                addDieselFuelSectionUseCase.execute(dieselFuelSection)
            }
            .subscribe()
    }
}