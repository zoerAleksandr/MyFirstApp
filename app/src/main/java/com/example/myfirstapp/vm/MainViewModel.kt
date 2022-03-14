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

    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()

/*    fun getData(): LiveData<MutableList<Itinerary>> {
        return liveDataToObserve
    }*/

    fun getData(): LiveData<AppState> {
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
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(1000)
            liveDataToObserve.postValue(AppState.Success(repository.getDataFromLocal()))
        }.start()
    }

    // Для добавления и предоставления списка из репозитория
    fun getDataAdd() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(500)
//            repository.addData()
            liveDataToObserve.postValue(AppState.Success(repository.getDataFromLocal()))
        }.start()
    }


    // Для удаления и предоставления списка из репозитория
    fun getDataRemove(position: Int) {
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(2000)
            Log.e("Remove", "$position")
            repository.remove(position)
            liveDataToObserve.postValue(AppState.Success(repository.getDataFromLocal()))
        }.start()
    }
}