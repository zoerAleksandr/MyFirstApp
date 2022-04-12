package com.example.myfirstapp.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfirstapp.Repository.Repository
import com.example.myfirstapp.Repository.RoomRepository
import com.example.myfirstapp.app.App.Companion.getItineraryDAO
import com.example.myfirstapp.data.Itinerary
import com.example.myfirstapp.room.EntityItinerary

class MainViewModel(
    private var repository: Repository = RoomRepository.newInstance(getItineraryDAO()),
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
) :
    ViewModel() {

    fun getData(): LiveData<AppState> {
        liveDataToObserve.value = AppState.Loading
        liveDataToObserve.postValue(
            AppState.Success(repository.getListItinerary())
        )
        return liveDataToObserve
    }

    fun addItinerary(itinerary: Itinerary) {
        repository.addItinerary(
            EntityItinerary.createNewEntityItinerary(
                Itinerary(
                    0,
                    itinerary.number,
                    itinerary.appearanceAtWork,
                    null,
                    true,
                    null,
                    mutableListOf(),
                    mutableListOf(),
                    mutableListOf(),
                )
            )
        )
    }


/*
    Принцып работы viewModel совместно с Observer
    Во фрагменте создается экземпляр ViewModel и вызывается один из методов, которые возвращают LiveData,
    далее указывается слушатель (Observer) и в лямбде описывается событие,
    которое должно произойти при изменении LiveData
    viewModel.getData().observe(viewLifecycleOwner, { renderDataSize(it) })
*/

    // Для предоставления списка из репозитория
//    fun getDataFromLocal() {
//        liveDataToObserve.value = AppState.Loading
//        Thread {
//            sleep(1000)
//            liveDataToObserve.postValue(AppState.Success(repository.getDataFromLocal()))
//        }.start()
//    }

    // Для добавления и предоставления списка из репозитория
//    fun getDataAdd() {
//        liveDataToObserve.value = AppState.Loading
//        Thread {
//            sleep(500)
////            repository.addData()
//            liveDataToObserve.postValue(AppState.Success(repository.getDataFromLocal()))
//        }.start()
//    }


    // Для удаления и предоставления списка из репозитория
//    fun getDataRemove(position: Int) {
//        liveDataToObserve.value = AppState.Loading
//        Thread {
//            sleep(2000)
//            Log.e("Remove", "$position")
//            repository.remove(position)
//            liveDataToObserve.postValue(AppState.Success(repository.getDataFromLocal()))
//        }.start()
//    }
}