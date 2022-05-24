package com.example.myfirstapp.ui.add_loco_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfirstapp.domain.entity.*
import com.example.myfirstapp.domain.usecase.locomotive.*
import com.example.myfirstapp.domain.usecase.section.diesel.*
import com.example.myfirstapp.domain.usecase.section.electric.*
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

class AddLocoViewModel(
    private val locomotiveDataID: String
) : ViewModel(), KoinComponent {
    private val compositeDisposable = CompositeDisposable()

    // for Diesel Section
    private val getListDieselFuelSectionUseCase: GetListDieselFuelSectionUseCase by inject()
    private val getDieselFuelSectionUseCase: GetDieselFuelSectionUseCase by inject()
    private val updateAcceptedDieselFuelSectionUseCase:
            UpdateAcceptedDieselFuelSectionUseCase by inject()
    private val updateDeliveryDieselFuelSectionUseCase:
            UpdateDeliveryDieselFuelSectionUseCase by inject()
    private val updateConsumptionDieselFuelUseCase: UpdateConsumptionDieselFuelUseCase by inject()

    // For Electric Section
    private val getElectricSectionUseCase: GetElectricSectionUseCase by inject()
    private val getElectricSectionListUseCase: GetElectricSectionUseCaseList by inject()
    private val updateAcceptedEnergyElectricSectionUseCase:
            UpdateAcceptedEnergyElectricSectionUseCase by inject()
    private val updateDeliveryEnergyElectricSectionUseCase:
            UpdateDeliveryEnergyElectricSectionUseCase by inject()
    private val updateConsumptionEnergyElectricSectionUseCase:
            UpdateConsumptionEnergyElectricSectionUseCase by inject()
    private val updateAcceptedRecoveryElectricSectionUseCase:
            UpdateAcceptedRecoveryElectricSectionUseCase by inject()
    private val updateDeliveryRecoveryElectricSectionUseCase:
            UpdateDeliveryRecoveryElectricSectionUseCase by inject()
    private val updateConsumptionRecoveryElectricSectionUseCase:
            UpdateConsumptionRecoveryElectricSectionUseCase by inject()

    // Series and Number
    private val updateSeriesLocoUseCase: UpdateSeriesLocoUseCase by inject()
    private val updateNumberLocoUseCase: UpdateNumberLocoUseCase by inject()

    // TypeOfTraction and CountSection
    private val updateTypeOfTractionUseCase: UpdateTypeOfTractionUseCase by inject()
    private val updateCountSectionUseCase: UpdateCountSectionUseCase by inject()

    // Calendar Acceptance and Delivery
    private val updateStartAcceptanceUseCase: UpdateStartAcceptanceUseCase by inject()
    private val updateEndAcceptanceUseCase: UpdateEndAcceptanceUseCase by inject()
    private val updateStartDeliveryUseCase: UpdateStartDeliveryUseCase by inject()
    private val updateEndDeliveryUseCase: UpdateEndDeliveryUseCase by inject()

    // Inventory
    private val updateBreakShoesUseCase: UpdateBreakShoesUseCase by inject()
    private val updateExtinguishersUseCase: UpdateExtinguishersUseCase by inject()

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

    fun saveBreakShoes(locomotiveDataID: String, count: Int?) {
        compositeDisposable.add(
            Single.just(locomotiveDataID)
                .observeOn(Schedulers.io())
                .concatMap {
                    updateBreakShoesUseCase.execute(locomotiveDataID, count)
                }
                .subscribe()
        )
    }
    fun saveExtinguishers(locomotiveDataID: String, count: Int?) {
        compositeDisposable.add(
            Single.just(locomotiveDataID)
                .observeOn(Schedulers.io())
                .concatMap {
                    updateExtinguishersUseCase.execute(locomotiveDataID, count)
                }
                .subscribe()
        )
    }

    fun saveStartAcceptance(locomotiveDataID: String, calendar: Calendar?) {
        compositeDisposable.add(
            Single.just(locomotiveDataID)
                .observeOn(Schedulers.io())
                .concatMap {
                    updateStartAcceptanceUseCase.execute(locomotiveDataID, calendar)
                }
                .subscribe()
        )
    }

    fun saveEndAcceptance(locomotiveDataID: String, calendar: Calendar?) {
        compositeDisposable.add(
            Single.just(locomotiveDataID)
                .observeOn(Schedulers.io())
                .concatMap {
                    updateEndAcceptanceUseCase.execute(locomotiveDataID, calendar)
                }
                .subscribe()
        )
    }

    fun saveStartDelivery(locomotiveDataID: String, calendar: Calendar?) {
        compositeDisposable.add(
            Single.just(locomotiveDataID)
                .observeOn(Schedulers.io())
                .concatMap {
                    updateStartDeliveryUseCase.execute(locomotiveDataID, calendar)
                }
                .subscribe()
        )
    }

    fun saveEndDelivery(locomotiveDataID: String, calendar: Calendar?) {
        compositeDisposable.add(
            Single.just(locomotiveDataID)
                .observeOn(Schedulers.io())
                .concatMap {
                    updateEndDeliveryUseCase.execute(locomotiveDataID, calendar)
                }
                .subscribe()
        )
    }

    fun saveTypeOfTraction(locomotiveDataID: String, typeOfTraction: TypeOfTraction) {
        compositeDisposable.add(
            Single.just(locomotiveDataID)
                .observeOn(Schedulers.io())
                .concatMap {
                    updateTypeOfTractionUseCase.execute(locomotiveDataID, typeOfTraction)
                }
                .subscribe()
        )
    }

    fun saveCountSection(locomotiveDataID: String, countSections: CountSections) {
        compositeDisposable.add(
            Single.just(locomotiveDataID)
                .observeOn(Schedulers.io())
                .concatMap {
                    updateCountSectionUseCase.execute(locomotiveDataID, countSections)
                }
                .subscribe()
        )
    }

    fun saveNumberLoco(locomotiveDataID: String, data: String?) {
        compositeDisposable.add(
            Single.just(locomotiveDataID)
                .observeOn(Schedulers.io())
                .concatMap {
                    updateNumberLocoUseCase.execute(it, data)
                }
                .subscribe()
        )
    }

    fun saveSeriesLoco(locomotiveDataID: String, data: String?) {
        compositeDisposable.add(
            Single.just(locomotiveDataID)
                .observeOn(Schedulers.io())
                .concatMap {
                    updateSeriesLocoUseCase.execute(it, data)
                }
                .subscribe()
        )
    }

    fun saveAcceptedInRoom(sectionIndex: Int, sectionID: String, value: Int?) {
        Single.just(sectionID)
            .observeOn(Schedulers.io())
            .concatMap {
                updateAcceptedDieselFuelSectionUseCase.execute(it, value)
            }
            .subscribeBy(
                onSuccess = {
                    postConsumptionDieselBySection(sectionIndex, sectionID)
                }
            )
    }

    fun saveDeliveryInRoom(sectionIndex: Int, sectionID: String, value: Int?) {
        Single.just(sectionID)
            .observeOn(Schedulers.io())
            .concatMap {
                // Обновляем в Room
                updateDeliveryDieselFuelSectionUseCase.execute(
                    it, value
                )
            }
            .subscribeBy(
                onSuccess = {
                    // считем расход секции
                    postConsumptionDieselBySection(sectionIndex, sectionID)
                }
            )
    }

    fun saveAcceptedEnergyInRoom(sectionIndex: Int, sectionID: String, value: Int?) {
        Single.just(sectionID)
            .observeOn(Schedulers.io())
            .concatMap {
                updateAcceptedEnergyElectricSectionUseCase.execute(sectionID, value)
            }
            .subscribeBy(
                onSuccess = {
                    postConsumptionEnergyBySection(sectionIndex, sectionID)
                }
            )
    }

    fun saveDeliveryEnergyInRoom(sectionIndex: Int, sectionID: String, value: Int?) {
        Single.just(sectionID)
            .observeOn(Schedulers.io())
            .concatMap {
                updateDeliveryEnergyElectricSectionUseCase.execute(sectionID, value)
            }
            .subscribeBy(
                onSuccess = {
                    postConsumptionEnergyBySection(sectionIndex, sectionID)
                }
            )
    }

    fun saveAcceptedRecoveryInRoom(sectionIndex: Int, sectionID: String, value: Int?) {
        Single.just(sectionID)
            .observeOn(Schedulers.io())
            .concatMap {
                updateAcceptedRecoveryElectricSectionUseCase.execute(sectionID, value)
            }
            .subscribeBy(
                onSuccess = {
                    postConsumptionRecoveryBySection(sectionIndex, sectionID)
                }
            )
    }

    fun saveDeliveryRecoveryInRoom(sectionIndex: Int, sectionID: String, value: Int?) {
        Single.just(sectionID)
            .observeOn(Schedulers.io())
            .concatMap {
                updateDeliveryRecoveryElectricSectionUseCase.execute(sectionID, value)
            }
            .subscribeBy(
                onSuccess = {
                    postConsumptionRecoveryBySection(sectionIndex, sectionID)
                }
            )
    }

    /** Получаем секцию по ID и после получчения отправляем View результат расхода по секции */
    private fun postConsumptionDieselBySection(sectionIndex: Int, sectionID: String) {
        requestDieselFuelSection(sectionID)
            .subscribeBy(
                onSuccess = { section ->
                    when (sectionIndex) {
                        0 -> {
                            liveDataDieselResultSecOne.postValue(
                                calculationBySectionDieselFuel(section)
                            )
                        }
                        1 -> {
                            liveDataDieselResultSecTwo.postValue(
                                calculationBySectionDieselFuel(section)
                            )
                        }
                        2 -> {
                            liveDataDieselResultSecThree.postValue(
                                calculationBySectionDieselFuel(section)
                            )
                        }
                        3 -> {
                            liveDataDieselResultSecFour.postValue(
                                calculationBySectionDieselFuel(section)
                            )
                        }
                    }
                }
            )
    }

    private fun postConsumptionEnergyBySection(sectionIndex: Int, sectionID: String) {
        requestElectricSection(sectionID)
            .subscribeBy(
                onSuccess = { section ->
                    when (sectionIndex) {
                        0 -> {
                            liveDataElectricEnergyElectricResultSecOne.postValue(
                                calculationBySectionElectric(section)
                            )
                        }
                        1 -> {
                            liveDataElectricEnergyElectricResultSecTwo.postValue(
                                calculationBySectionElectric(section)
                            )
                        }
                        2 -> {
                            liveDataElectricEnergyElectricResultSecThree.postValue(
                                calculationBySectionElectric(section)
                            )
                        }
                        3 -> {
                            liveDataElectricEnergyElectricResultSecFour.postValue(
                                calculationBySectionElectric(section)
                            )
                        }
                    }
                }
            )
    }

    private fun postConsumptionRecoveryBySection(sectionIndex: Int, sectionID: String) {
        requestElectricSection(sectionID)
            .subscribeBy(
                onSuccess = { section ->
                    when (sectionIndex) {
                        0 -> {
                            liveDataElectricRecoveryElectricResultSecOne.postValue(
                                calculationBySectionRecovery(section)
                            )
                        }
                        1 -> {
                            liveDataElectricRecoveryElectricResultSecTwo.postValue(
                                calculationBySectionRecovery(section)
                            )
                        }
                        2 -> {
                            liveDataElectricRecoveryElectricResultSecThree.postValue(
                                calculationBySectionRecovery(section)
                            )
                        }
                        3 -> {
                            liveDataElectricRecoveryElectricResultSecFour.postValue(
                                calculationBySectionRecovery(section)
                            )
                        }
                    }
                }
            )
    }

    /** считаем расход и определям значение которое будет показано во View расхода по секции*/
    private fun calculationBySectionDieselFuel(section: DieselFuelSection): StateSection {
        return if (section.accepted == null || section.delivery == null) {
            updateConsamptionDieselFuelBySection(section.sectionID, null)
            StateSection.EmptyData(true)
        } else if (section.supply == null && section.accepted!! < section.delivery!!) {
            updateConsamptionDieselFuelBySection(section.sectionID, null)
            StateSection.Error("Показание сдачи больше показания приемки")
        } else if (section.supply != null && section.accepted!!.plus(section.supply!!) < section.delivery!!) {
            updateConsamptionDieselFuelBySection(section.sectionID, null)
            StateSection.Error("Показание сдачи больше показания приемки")
        } else {
            val result = section.accepted!!.plus(section.supply ?: 0).minus(section.delivery!!)
            updateConsamptionDieselFuelBySection(section.sectionID, result)
            StateSection.Success(result)
        }
    }

    private fun calculationBySectionElectric(section: ElectricSection): StateSection {
        return if (section.acceptanceEnergy == null || section.deliveryEnergy == null) {
            updateConsumptionEnergyElectricSection(section.sectionID, null)
            StateSection.EmptyData(true)
        } else if (section.acceptanceEnergy!! > section.deliveryEnergy!!) {
            updateConsumptionEnergyElectricSection(section.sectionID, null)
            StateSection.Error("Показание сдачи меньше показания приемки")
        } else {
            val result = section.deliveryEnergy!!.minus(section.acceptanceEnergy!!)
            updateConsumptionEnergyElectricSection(section.sectionID, result)
            StateSection.Success(result)
        }
    }

    private fun calculationBySectionRecovery(section: ElectricSection): StateSection {
        return if (section.acceptanceRecovery == null || section.deliveryRecovery == null) {
            updateConsumptionRecoveryElectricSection(section.sectionID, null)
            StateSection.EmptyData(true)
        } else if (section.acceptanceRecovery!! > section.deliveryRecovery!!) {
            updateConsumptionRecoveryElectricSection(section.sectionID, null)
            StateSection.Error("Показание сдачи меньше показания приемки")
        } else {
            val result = section.deliveryRecovery!!.minus(section.acceptanceRecovery!!)
            updateConsumptionRecoveryElectricSection(section.sectionID, result)
            StateSection.Success(result)
        }
    }

    /** Считаем общий расход и отправляем результат во View */
    private fun calculationTotalConsumption() {
        compositeDisposable.add(
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
                            liveDataDieselResultTotal.postValue(StateSection.Success(total))
                        } else {
                            liveDataDieselResultTotal.postValue(StateSection.EmptyData(true))
                        }
                    }
                )
        )
    }

    private fun calculationTotalConsumptionEnergy() {
        compositeDisposable.add(
            Single.just(locomotiveDataID)
                .observeOn(Schedulers.io())
                .concatMap {
                    getElectricSectionListUseCase.execute(locomotiveDataID)
                }
                .subscribeBy(
                    onSuccess = {
                        var total = 0
                        for (section in it) {
                            section.consumptionEnergy?.let { cons ->
                                total += cons
                            }
                        }
                        if (total > 0) {
                            liveDataElectricEnergyElectricResultTotal.postValue(
                                StateSection.Success(total)
                            )
                        } else {
                            liveDataElectricEnergyElectricResultTotal.postValue(
                                StateSection.EmptyData(true)
                            )
                        }
                    }
                )
        )
    }

    private fun calculationTotalConsumptionRecovery() {
        compositeDisposable.add(
            Single.just(locomotiveDataID)
                .observeOn(Schedulers.io())
                .concatMap {
                    getElectricSectionListUseCase.execute(locomotiveDataID)
                }
                .subscribeBy(
                    onSuccess = {
                        var total = 0
                        for (section in it) {
                            section.consumptionRecovery?.let { cons ->
                                total += cons
                            }
                        }
                        if (total > 0) {
                            liveDataElectricRecoveryElectricResultTotal.postValue(
                                StateSection.Success(total)
                            )
                        } else {
                            liveDataElectricRecoveryElectricResultTotal.postValue(
                                StateSection.EmptyData(true)
                            )
                        }
                    }
                )
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

    private fun requestElectricSection(sectionID: String): Single<ElectricSection> {
        return Single.just(sectionID)
            .observeOn(Schedulers.io())
            .concatMap { getElectricSectionUseCase.execute(sectionID) }
    }

    // Обновляем данные расхода по секции в Room
    private fun updateConsamptionDieselFuelBySection(sectionID: String, data: Int?) {
        compositeDisposable.add(
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
        )
    }

    private fun updateConsumptionEnergyElectricSection(sectionID: String, data: Int?) {
        compositeDisposable.add(
            Single.just(sectionID)
                .observeOn(Schedulers.io())
                .concatMap {
                    updateConsumptionEnergyElectricSectionUseCase.execute(sectionID, data)
                }
                .subscribeBy(
                    onSuccess = {
                        calculationTotalConsumptionEnergy()
                    }
                )
        )
    }

    private fun updateConsumptionRecoveryElectricSection(sectionID: String, data: Int?) {
        compositeDisposable.add(
            Single.just(sectionID)
                .observeOn(Schedulers.io())
                .concatMap {
                    updateConsumptionRecoveryElectricSectionUseCase.execute(sectionID, data)
                }
                .subscribeBy(
                    onSuccess = {
                        calculationTotalConsumptionRecovery()
                    }
                )
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}