package com.example.myfirstapp.ui.add_loco_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfirstapp.domain.entity.DieselFuelSection
import com.example.myfirstapp.domain.usecase.locomotive.AddLocomotiveDataUseCase
import com.example.myfirstapp.domain.usecase.section.diesel.*
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class AddLocoViewModel(
    private val getListDieselFuelSectionUseCase: GetListDieselFuelSectionUseCase,
    private val getDieselFuelSectionUseCase: GetDieselFuelSectionUseCase,
    private val updateAcceptedDieselFuelSectionUseCase: UpdateAcceptedDieselFuelSectionUseCase,
    private val updateDeliveryDieselFuelSectionUseCase: UpdateDeliveryDieselFuelSectionUseCase,
    private val updateConsumptionDieselFuelUseCase: UpdateConsumptionDieselFuelUseCase,
    private val addLocomotiveDataUseCase: AddLocomotiveDataUseCase,
    private val addDieselFuelSectionUseCase: AddDieselFuelSectionUseCase,
    private val locomotiveDataID: String
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val liveDataResultSecOne: MutableLiveData<StateAddLocoDieselFuel> = MutableLiveData()
    private val liveDataResultSecTwo: MutableLiveData<StateAddLocoDieselFuel> = MutableLiveData()
    private val liveDataResultSecThree: MutableLiveData<StateAddLocoDieselFuel> = MutableLiveData()
    private val liveDataResultSecFour: MutableLiveData<StateAddLocoDieselFuel> = MutableLiveData()

    private val liveDataResultTotal: MutableLiveData<StateAddLocoDieselFuel> = MutableLiveData()

    fun getResultSecOne(): LiveData<StateAddLocoDieselFuel> {
        return liveDataResultSecOne
    }

    fun getResultSecTwo(): LiveData<StateAddLocoDieselFuel> {
        return liveDataResultSecTwo
    }

    fun getResultSecThree(): LiveData<StateAddLocoDieselFuel> {
        return liveDataResultSecThree
    }

    fun getResultSecFour(): LiveData<StateAddLocoDieselFuel> {
        return liveDataResultSecFour
    }

    fun getTotalResult(): LiveData<StateAddLocoDieselFuel> {
        return liveDataResultTotal
    }

    fun saveAcceptedInRoom(
        locomotiveDataID: String,
        sectionIndex: Int,
        sectionID: String,
        value: Int?
    ) {
        Single.just(sectionID)
            .observeOn(Schedulers.io())
            .concatMap {
                updateAcceptedDieselFuelSectionUseCase.execute(
                    it, value
                )
            }
            .concatMap {
                getListDieselFuelSectionUseCase.execute(locomotiveDataID)
            }
            .subscribeBy(
                onSuccess = {
                    postConsumptionBySection(sectionIndex, sectionID)
                }
            )
    }

    fun saveDeliveryInRoom(
        locomotiveDataID: String,
        sectionIndex: Int,
        sectionID: String,
        value: Int?
    ) {
        Single.just(sectionID)
            .observeOn(Schedulers.io())
            .concatMap {
                // Обновляем в Room
                updateDeliveryDieselFuelSectionUseCase.execute(
                    it, value
                )
            }
            .concatMap {
                // Получаем новые данные
                getListDieselFuelSectionUseCase.execute(locomotiveDataID)
            }
            .subscribeBy(
                onSuccess = {
                    // считем расход секции
                    postConsumptionBySection(sectionIndex, sectionID)
                }
            )
    }

    /** Получаем секцию по ID и после получчения отправляем View результат расхода по секции */
    private fun postConsumptionBySection(
        sectionIndex: Int,
        sectionID: String
    ) {
        requestDieselFuelSection(sectionID)
            .subscribeBy(
                onSuccess = { section ->
                    when (sectionIndex) {
                        0 -> {
                            liveDataResultSecOne.postValue(
                                calculationBySectionDieselFuel(section)
                            )
                        }
                        1 -> {
                            liveDataResultSecTwo.postValue(
                                calculationBySectionDieselFuel(section)
                            )
                        }
                        2 -> {
                            liveDataResultSecThree.postValue(
                                calculationBySectionDieselFuel(section)
                            )
                        }
                        3 -> {
                            liveDataResultSecFour.postValue(
                                calculationBySectionDieselFuel(section)
                            )
                        }
                    }
                }
            )
    }

    /** считаем расход и
     * Определям значение которое будет показано во View расхода по секции*/
    private fun calculationBySectionDieselFuel(section: DieselFuelSection): StateAddLocoDieselFuel {
        return if (section.accepted == null || section.delivery == null) {
            updateConsamptionDieselFuelBySection(section.sectionID, null)
            StateAddLocoDieselFuel.EmptyData(true)
        } else if (section.supply == null && section.accepted!! < section.delivery!!) {
            updateConsamptionDieselFuelBySection(section.sectionID, null)
            StateAddLocoDieselFuel.Error("Показание сдачи меньше показания приемки")
        } else if (section.supply != null && section.accepted!!.plus(section.supply!!) < section.delivery!!) {
            updateConsamptionDieselFuelBySection(section.sectionID, null)
            StateAddLocoDieselFuel.Error("Показание сдачи меньше показания приемки")
        } else {
            val result = section.accepted!!.plus(section.supply ?: 0).minus(section.delivery!!)
            updateConsamptionDieselFuelBySection(section.sectionID, result)
            StateAddLocoDieselFuel.Success(result)
        }
    }

    /** Считаем общий расход и отправляем результат во View */
    private fun calculationTotalConsumption() {
        Single.just(locomotiveDataID)
            .observeOn(Schedulers.io())
            .concatMap {
                getListDieselFuelSectionUseCase.execute(locomotiveDataID)
            }
            .subscribeBy(
                onSuccess = {
                    var total = 0
                    for (section in it) {
                        section.consumption?.let { cons ->
                            total += cons
                        }
                    }
                    if (total > 0) {
                        liveDataResultTotal.postValue(StateAddLocoDieselFuel.Success(total))
                    }else{
                        liveDataResultTotal.postValue(StateAddLocoDieselFuel.EmptyData(true))
                    }
                }
            )
    }

    // запрос данных секции по ID
    private fun requestDieselFuelSection(sectionID: String): Single<DieselFuelSection> {
        return Single.just(sectionID)
            .observeOn(Schedulers.io())
            .concatMap {
                getDieselFuelSectionUseCase.execute(sectionID)
            }
    }

    // Обновляем данные расхода по секции в Room
    private fun updateConsamptionDieselFuelBySection(sectionID: String, data: Int?) {
         Single.just(sectionID)
            .observeOn(Schedulers.io())
            .concatMap {
                updateConsumptionDieselFuelUseCase.execute(it, data)
            }
            .subscribeBy(
                onSuccess = {
                    calculationTotalConsumption()
                }
            )
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}