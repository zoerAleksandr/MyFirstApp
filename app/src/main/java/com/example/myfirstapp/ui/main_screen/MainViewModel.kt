package com.example.myfirstapp.ui.main_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfirstapp.domain.usecase.itinerary.GetItineraryListByMonth
import com.example.myfirstapp.utils.AppState
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.component.KoinComponent

class MainViewModel(
    private val getItineraryListUseCase: GetItineraryListByMonth
) : ViewModel(), KoinComponent {

    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    private val compositeDisposable = CompositeDisposable()
    fun getCurrentData(month: Int): LiveData<AppState> {
        requestItineraryListByMonth(month)
        return liveDataToObserve
    }

    private fun requestItineraryListByMonth(month: Int) {
        liveDataToObserve.value = AppState.Loading
        compositeDisposable.add(
            Single.just(month)
                .observeOn(Schedulers.io())
                .concatMap {
                    getItineraryListUseCase.execute(month)
                }
                .subscribeBy(
                    onSuccess = { answerList ->
                        liveDataToObserve.postValue(
                            AppState.Success(answerList)
                        )
                    },
                    onError = { throwable ->
                        liveDataToObserve.postValue(
                            AppState.Error(throwable)
                        )
                    }
                )
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}