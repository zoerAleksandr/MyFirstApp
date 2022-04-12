package com.example.myfirstapp.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfirstapp.Repository.RoomRepository
import com.example.myfirstapp.app.App.Companion.getItineraryDAO
import com.example.myfirstapp.data.Station

class ViewModelAddTrainFragment : ViewModel() {
    private val lifeDataToObserve: MutableLiveData<AppStateAddTrain> = MutableLiveData()
    private val repository = RoomRepository.newInstance(
        getItineraryDAO()
    )

    fun getData(trainDataID: Long): LiveData<AppStateAddTrain> {
        lifeDataToObserve.value = AppStateAddTrain.Success(repository.getListStation(trainDataID))
        return lifeDataToObserve
    }

    fun insert(station: Station) {

    }

    fun remove(station: Station) {

    }
}