package com.example.myfirstapp.ui.main_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfirstapp.domain.entity.Itinerary
import com.example.myfirstapp.domain.usecase.itinerary.DeleteItineraryUseCase
import com.example.myfirstapp.domain.usecase.itinerary.GetItineraryListByMonth
import com.example.myfirstapp.utils.AppState
import kotlinx.coroutines.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainViewModel : ViewModel(), KoinComponent {
    private val getItineraryListUseCase: GetItineraryListByMonth by inject()
    private val deleteItineraryUseCase: DeleteItineraryUseCase by inject()
    private val liveData: MutableLiveData<AppState> = MutableLiveData()

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

    private fun getItineraryListByMonth(month: Int) {
        liveData.postValue(AppState.Loading)
        scope.launch {
            kotlin.runCatching { getItineraryListUseCase.execute(month) }
                .onSuccess { liveData.postValue(AppState.Success(it)) }
                .onFailure { liveData.postValue(AppState.Error(it)) }
        }
    }

    fun deleteItinerary(itinerary: Itinerary) {
        scope.launch {
            kotlin.runCatching { deleteItineraryUseCase.execute(itinerary) }
                .onSuccess {
                    // TODO
                }
                .onFailure { liveData.postValue(AppState.Error(it)) }
        }

    }

    override fun onCleared() {
        super.onCleared()
        scope.coroutineContext.cancelChildren()
    }
}