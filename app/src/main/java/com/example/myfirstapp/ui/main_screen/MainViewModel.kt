package com.example.myfirstapp.ui.main_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirstapp.domain.entity.Itinerary
import com.example.myfirstapp.domain.usecase.itinerary.AddItineraryUseCase
import com.example.myfirstapp.domain.usecase.itinerary.GetItineraryListByMonth
import com.example.myfirstapp.utils.AppState
import kotlinx.coroutines.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

class MainViewModel : ViewModel(), KoinComponent {
    private val getItineraryListUseCase: GetItineraryListByMonth by inject()
    private val addItineraryUseCase: AddItineraryUseCase by inject()
    private val liveData: MutableLiveData<AppState> = MutableLiveData()

//    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
//        liveData.postValue(AppState.Error(throwable))
//    }

    private val scope = CoroutineScope(
        Dispatchers.IO + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            liveData.postValue(AppState.Error(throwable))
        }
    )

    fun getCurrentData(month: Int): LiveData<AppState> {
        getItineraryListByMonth(month)
        return liveData
    }

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
        scope.launch {
            kotlin.runCatching { addItineraryUseCase.execute(itinerary) }
                .onSuccess {
                    //TODO
                }
                .onFailure {
                    //TODO
                }
        }
    }

    private fun getItineraryListByMonth(month: Int) {
        liveData.postValue(AppState.Loading)
        scope.launch {
            kotlin.runCatching { getItineraryListUseCase.execute(month) }
                .onSuccess { liveData.postValue(AppState.Success(it)) }
                .onFailure { liveData.postValue(AppState.Error(it)) }
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.coroutineContext.cancelChildren()
    }
}