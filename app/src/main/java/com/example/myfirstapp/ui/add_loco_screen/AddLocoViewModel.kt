package com.example.myfirstapp.ui.add_loco_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfirstapp.domain.entity.CountSections
import com.example.myfirstapp.domain.entity.DieselFuelSection
import com.example.myfirstapp.domain.entity.LocomotiveData
import com.example.myfirstapp.domain.usecase.locomotive.AddLocomotiveDataUseCase
import com.example.myfirstapp.domain.usecase.section.diesel.AddDieselFuelSectionUseCase
import com.example.myfirstapp.domain.usecase.section.diesel.GetListDieselFuelSectionUseCase
import com.example.myfirstapp.domain.usecase.section.diesel.UpdateAcceptedDieselFuelSectionUseCase
import com.example.myfirstapp.domain.usecase.section.diesel.UpdateDeliveryDieselFuelSectionUseCase
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class AddLocoViewModel(
    private val getListDieselFuelSectionUseCase: GetListDieselFuelSectionUseCase,
    private val updateAcceptedDieselFuelSectionUseCase: UpdateAcceptedDieselFuelSectionUseCase,
    private val updateDeliveryDieselFuelSectionUseCase: UpdateDeliveryDieselFuelSectionUseCase,
    private val addLocomotiveDataUseCase: AddLocomotiveDataUseCase,
    private val addDieselFuelSectionUseCase: AddDieselFuelSectionUseCase,
    private val locomotiveDataID: String
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val liveDataResultSecOne: MutableLiveData<StateAddLocoDieselFuel> = MutableLiveData()
    private val liveDataResultSecTwo: MutableLiveData<Int> = MutableLiveData()
    private val liveDataResultSecThree: MutableLiveData<Int> = MutableLiveData()
    private val liveDataResultSecFour: MutableLiveData<Int> = MutableLiveData()

    private val liveDataResultTotal: MutableLiveData<Int> = MutableLiveData()

    fun getResultSecOne(): LiveData<StateAddLocoDieselFuel> {
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

    fun saveAccepted(sectionID: String, value: Int) {
        updateAcceptedDieselFuelSectionUseCase.execute(
            sectionID, value
        )
    }

    // Расчет расхода по секциям приемка
    fun acceptedBySection(section: CountSections, value: Int) {
        when (section) {
            CountSections.OneSection -> run {
                calculationConsamptionDieselFuel(value, section.index)
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

    private fun calculationConsamptionDieselFuel(value: Int, section: Int) {
        compositeDisposable.add(
            Single.just(locomotiveDataID)
                .observeOn(Schedulers.io())
                .concatMap {
                    getListDieselFuelSectionUseCase.execute(it)
                }
                .subscribeBy(
                    onSuccess = { locoData ->
                        val delivery: Int? = locoData[section].delivery
                        val supply: Int? = locoData[section].supply
                        if (delivery != null) {
                            liveDataResultSecOne.postValue(
                                StateAddLocoDieselFuel.Success(
                                    calculationBySectionDieselFuel(
                                        value,
                                        delivery,
                                        supply
                                    )
                                )
                            )
                        }
                    },
                    onError = {
                        liveDataResultSecOne.postValue(StateAddLocoDieselFuel.Error("Ошибка получения данных"))
                    }
                )
        )
    }

    private fun calculationBySectionDieselFuel(accepted: Int, delivery: Int, supply: Int?): Int {
        return accepted.plus(supply ?: 0).minus(delivery)
//        return if (accepted == null || delivery == null){
//            StateAddLocoDieselFuel.Error(null)
//        }else if(accepted < delivery && supply == null){
//            StateAddLocoDieselFuel.Error("Сдача больше приемки")
//        }else if (supply != null && accepted.plus(supply) < delivery){
//            StateAddLocoDieselFuel.Error("Сдача больше приемки")
//        }else if (accepted > delivery && supply == null){
//            StateAddLocoDieselFuel.Success(accepted, delivery, accepted.minus(delivery))
//        }else if (supply != null && accepted.plus(supply) > delivery){
//            StateAddLocoDieselFuel.Success(accepted, delivery, accepted.plus(supply).minus(delivery))
//        }else{
//            StateAddLocoDieselFuel.Error(null)
//        }
//
    }

    // Расчет общего расхода
    fun calculationTotalConsumption(listConsumptionBySection: List<Int>) {
        var total = 0
        for (consumptionBySection in listConsumptionBySection) {
            total += consumptionBySection
        }
        liveDataResultTotal.postValue(total)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}