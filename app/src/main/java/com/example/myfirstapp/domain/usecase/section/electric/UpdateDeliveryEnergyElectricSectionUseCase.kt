package com.example.myfirstapp.domain.usecase.section.electric

import com.example.myfirstapp.domain.repository.IRepository
import io.reactivex.rxjava3.core.Single

class UpdateDeliveryEnergyElectricSectionUseCase(private val repository: IRepository) {
    fun execute(sectionID: String, delivery: Int?): Single<Int> {
        return repository.updateDeliveryEnergyElectricSection(sectionID, delivery)
    }
}