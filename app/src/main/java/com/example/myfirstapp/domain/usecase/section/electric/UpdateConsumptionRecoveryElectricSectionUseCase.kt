package com.example.myfirstapp.domain.usecase.section.electric

import com.example.myfirstapp.domain.repository.IRepository
import io.reactivex.rxjava3.core.Single

class UpdateConsumptionRecoveryElectricSectionUseCase(private val repository: IRepository) {
    fun execute(sectionID: String, consumption: Int?): Single<Int> {
        return repository.updateConsumptionRecoveryElectricSection(sectionID, consumption)
    }
}