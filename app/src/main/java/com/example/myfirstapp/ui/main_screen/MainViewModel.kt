package com.example.myfirstapp.ui.main_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfirstapp.App.Companion.getItineraryDAO
import com.example.myfirstapp.data.room.EntityItinerary
import com.example.myfirstapp.data.room.RoomIRepository
import com.example.myfirstapp.domain.IRepository
import com.example.myfirstapp.domain.Itinerary
import com.example.myfirstapp.utils.AppState

class MainViewModel(
    private var IRepository: IRepository = RoomIRepository.newInstance(getItineraryDAO()),
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
) :
    ViewModel() {

    fun getData(): LiveData<AppState> {
        liveDataToObserve.value = AppState.Loading
        liveDataToObserve.postValue(
            AppState.Success(IRepository.getListItinerary())
        )
        return liveDataToObserve
    }

    fun addItinerary(itinerary: Itinerary) {
        IRepository.addItinerary(
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