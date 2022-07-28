package com.example.myfirstapp.ui.add_loco_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.koin.core.component.KoinComponent

class AddLocoViewModel(
    private val locomotiveDataID: String
) : ViewModel(), KoinComponent {
    private val compositeDisposable = CompositeDisposable()


    // LiveData for Diesel Section
    private val liveDataDieselResultSecOne: MutableLiveData<StateSection> = MutableLiveData()
    private val liveDataDieselResultSecTwo: MutableLiveData<StateSection> = MutableLiveData()
    private val liveDataDieselResultSecThree: MutableLiveData<StateSection> = MutableLiveData()
    private val liveDataDieselResultSecFour: MutableLiveData<StateSection> = MutableLiveData()
    private val liveDataDieselResultTotal: MutableLiveData<StateSection> = MutableLiveData()

    // LiveData for Electric Section
    private val liveDataElectricEnergyElectricResultSecOne: MutableLiveData<StateSection> =
        MutableLiveData()
    private val liveDataElectricEnergyElectricResultSecTwo: MutableLiveData<StateSection> =
        MutableLiveData()
    private val liveDataElectricEnergyElectricResultSecThree: MutableLiveData<StateSection> =
        MutableLiveData()
    private val liveDataElectricEnergyElectricResultSecFour: MutableLiveData<StateSection> =
        MutableLiveData()
    private val liveDataElectricEnergyElectricResultTotal: MutableLiveData<StateSection> =
        MutableLiveData()

    private val liveDataElectricRecoveryElectricResultSecOne: MutableLiveData<StateSection> =
        MutableLiveData()
    private val liveDataElectricRecoveryElectricResultSecTwo: MutableLiveData<StateSection> =
        MutableLiveData()
    private val liveDataElectricRecoveryElectricResultSecThree: MutableLiveData<StateSection> =
        MutableLiveData()
    private val liveDataElectricRecoveryElectricResultSecFour: MutableLiveData<StateSection> =
        MutableLiveData()
    private val liveDataElectricRecoveryElectricResultTotal: MutableLiveData<StateSection> =
        MutableLiveData()


    // GetLiveData for Diesel Section
    fun getResultDieselSecOne(): LiveData<StateSection> {
        return liveDataDieselResultSecOne
    }

    fun getResultDieselSecTwo(): LiveData<StateSection> {
        return liveDataDieselResultSecTwo
    }

    fun getResultDieselSecThree(): LiveData<StateSection> {
        return liveDataDieselResultSecThree
    }

    fun getResultDieselSecFour(): LiveData<StateSection> {
        return liveDataDieselResultSecFour
    }

    fun getTotalDieselResult(): LiveData<StateSection> {
        return liveDataDieselResultTotal
    }

    // GetLiveData for Electric Section
    fun getResultEnergyElectricSecOne(): LiveData<StateSection> {
        return liveDataElectricEnergyElectricResultSecOne
    }

    fun getResultEnergyElectricSecTwo(): LiveData<StateSection> {
        return liveDataElectricEnergyElectricResultSecTwo
    }

    fun getResultEnergyElectricSecThree(): LiveData<StateSection> {
        return liveDataElectricEnergyElectricResultSecThree
    }

    fun getResultEnergyElectricSecFour(): LiveData<StateSection> {
        return liveDataElectricEnergyElectricResultSecFour
    }

    fun getTotalEnergyElectricResult(): LiveData<StateSection> {
        return liveDataElectricEnergyElectricResultTotal
    }

    fun getResultRecoveryElectricSecOne(): LiveData<StateSection> {
        return liveDataElectricRecoveryElectricResultSecOne
    }

    fun getResultRecoveryElectricSecTwo(): LiveData<StateSection> {
        return liveDataElectricRecoveryElectricResultSecTwo
    }

    fun getResultRecoveryElectricSecThree(): LiveData<StateSection> {
        return liveDataElectricRecoveryElectricResultSecThree
    }

    fun getResultRecoveryElectricSecFour(): LiveData<StateSection> {
        return liveDataElectricRecoveryElectricResultSecFour
    }

    fun getTotalRecoveryElectricResult(): LiveData<StateSection> {
        return liveDataElectricRecoveryElectricResultTotal
    }
}
