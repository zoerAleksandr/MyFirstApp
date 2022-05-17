package com.example.myfirstapp.ui.add_loco_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfirstapp.domain.entity.CountSections

class AddLocoViewModel : ViewModel() {
    private val liveDataResultSecOne: MutableLiveData<Int> = MutableLiveData()
    private val liveDataResultSecTwo: MutableLiveData<Int> = MutableLiveData()
    private val liveDataResultSecThree: MutableLiveData<Int> = MutableLiveData()
    private val liveDataResultSecFour: MutableLiveData<Int> = MutableLiveData()

    private val liveDataResultTotal: MutableLiveData<Int> = MutableLiveData()

    fun getResultSecOne(): LiveData<Int> {
        return liveDataResultSecOne
    }

    fun getResultSecTwo(): LiveData<Int> {
        return liveDataResultSecTwo
    }

    fun getResultSecThree(): LiveData<Int> {
        return liveDataResultSecThree
    }

    fun getResultSecFour(): LiveData<Int> {
        return liveDataResultSecFour
    }

    fun getTotalResult(): LiveData<Int> {
        return liveDataResultTotal
    }

    // Расчет расхода по секциям
    fun calculationConsumptionBySection(sectionNumber: CountSections, value: Int) {
        when (sectionNumber) {
            CountSections.OneSection -> run {
                liveDataResultSecOne.postValue(1 + value)
            }
            CountSections.TwoSection -> run {
                liveDataResultSecTwo.postValue(2 + value)
            }
            CountSections.ThreeSection -> run {
                liveDataResultSecThree.postValue(3 + value)
            }
            CountSections.FourSection -> run {
                liveDataResultSecFour.postValue(4 + value)
            }
        }
    }

    // Расчет общего расхода
    fun calculationTotalConsumption(listConsumptionBySection: List<Int>) {
        var total = 0
        for (consumptionBySection in listConsumptionBySection) {
            total += consumptionBySection
        }
        liveDataResultTotal.postValue(total)
    }
}