package com.example.myfirstapp.ui.add_train_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfirstapp.App.Companion.getItineraryDAO
import com.example.myfirstapp.data.room.RoomIRepository
import com.example.myfirstapp.domain.entity.Station
import com.example.myfirstapp.utils.AppStateAddTrain

class ViewModelAddTrainFragment : ViewModel() {
    private val lifeDataToObserve: MutableLiveData<AppStateAddTrain> = MutableLiveData()
    private val repository = RoomIRepository.newInstance(
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