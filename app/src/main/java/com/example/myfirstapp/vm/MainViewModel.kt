package com.example.myfirstapp.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfirstapp.Repository.Repository
import com.example.myfirstapp.Repository.RepositoryImpl
import com.example.myfirstapp.data.Itinerary
import java.lang.Thread.sleep

class MainViewModel() :
    ViewModel() {

    private val repository: Repository = RepositoryImpl.newInstance()
    private val liveDataToObserve: MutableLiveData<List<Itinerary>> = MutableLiveData()

    fun getData(): LiveData<List<Itinerary>> {
        return liveDataToObserve
    }

/*  Принцып работы viewModel совместно с Observer
*   Во фрагменте создается экземпляр ViewModel и вызывается один из методов, которые возвращают LiveData,
*   далее указывается слушатель (Observer) и в лямбде описывается событие,
*   которое должно произойти при изменении LiveData
*   viewModel.getData().observe(viewLifecycleOwner, { renderDataSize(it) })*/

    // Для предоставления списка из репозитория
    fun getDataFromLocal() {
        Thread {
            sleep(1000)
            liveDataToObserve.postValue(repository.getDataFromLocal())
        }.start()
    }

    // Для обновления и предоставления списка из репощитория
    fun getDataUpdate(number: String) {
        Thread {
            repository.addData(Itinerary(number))
            liveDataToObserve.postValue(repository.getDataFromLocal())
        }.start()
    }
}