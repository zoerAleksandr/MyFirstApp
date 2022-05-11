package com.example.myfirstapp.ui.add_train_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfirstapp.domain.entity.Station
import com.example.myfirstapp.utils.AppStateAddTrain

class ViewModelAddTrainFragment : ViewModel() {
    private val lifeDataToObserve: MutableLiveData<AppStateAddTrain> = MutableLiveData()

    fun getData(trainDataID: Long): LiveData<AppStateAddTrain> {
        return lifeDataToObserve
    }

    fun insert(station: Station) {

    }

    fun remove(station: Station) {

    }
}