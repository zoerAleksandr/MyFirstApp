package com.example.myfirstapp.ui.add_itinerary_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.myfirstapp.domain.entity.Itinerary
import com.example.myfirstapp.domain.usecase.itinerary.AddItineraryUseCase
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class AddItineraryViewModel(
    private val addItineraryUseCase: AddItineraryUseCase
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    fun saveItinerary(itinerary: Itinerary) {
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
}