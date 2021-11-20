package com.example.myfirstapp.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfirstapp.Repository.Repository
import com.example.myfirstapp.Repository.RepositoryImpl
import com.example.myfirstapp.data.Itinerary
import java.lang.Thread.sleep

class MainViewModel(private var repository: Repository = RepositoryImpl.newInstance()) :
    ViewModel() {

    private val liveDataToObserve: MutableLiveData<MutableList<Itinerary>> = MutableLiveData()

    fun getData(): LiveData<MutableList<Itinerary>> {
        return liveDataToObserve
    }

/*
    Принцып работы viewModel совместно с Observer
    Во фрагменте создается экземпляр ViewModel и вызывается один из методов, которые возвращают LiveData,
    далее указывается слушатель (Observer) и в лямбде описывается событие,
    которое должно произойти при изменении LiveData
    viewModel.getData().observe(viewLifecycleOwner, { renderDataSize(it) })
*/

    // Для предоставления списка из репозитория
    fun getDataFromLocal() {
        Thread {
            sleep(1000)
            liveDataToObserve.postValue(repository.getDataFromLocal())
            Log.e("1", "${repository.hashCode()}")
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