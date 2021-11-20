package com.example.myfirstapp.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfirstapp.Repository.Repository
import com.example.myfirstapp.Repository.RepositoryImpl
import com.example.myfirstapp.data.Itinerary
import java.lang.Thread.sleep

class MainViewModel(private val repository: Repository = RepositoryImpl.newInstance()) :
    ViewModel() {

    private val liveDataToObserve: MutableLiveData<List<Itinerary>> = MutableLiveData()
    private val liveDataSizeToObserve: MutableLiveData<Int> = MutableLiveData()
    private val liveDataItineraryToObserve: MutableLiveData<Itinerary> = MutableLiveData()

    fun getData(): LiveData<List<Itinerary>> {
        return liveDataToObserve
    }


    fun getDataItinerary(): LiveData<Itinerary> {
        return liveDataItineraryToObserve
    }


    fun getDataSize(): LiveData<Int> {
        return liveDataSizeToObserve
    }

    fun getDataFromLocal() {
        // liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(2000)
            liveDataToObserve.postValue(repository.getDataFromLocal())
            sleep(5000)
            liveDataToObserve.postValue(repository.getNewData())
        }.start()
    }


    fun setDataInLocal(data: Itinerary) {
        Thread {
            repository.addData(data)
        }.start()
    }

    fun getRepoSize() {
        Thread {
            sleep(1000)
            liveDataSizeToObserve.postValue(repository.getSize())
        }.start()
    }

}