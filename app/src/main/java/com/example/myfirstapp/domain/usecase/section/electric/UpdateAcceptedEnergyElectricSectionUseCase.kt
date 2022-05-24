package com.example.myfirstapp.domain.usecase.section.electric

import com.example.myfirstapp.domain.repository.IRepository
import io.reactivex.rxjava3.core.Single

class UpdateAcceptedEnergyElectricSectionUseCase(private val repository: IRepository) {
    fun execute(sectionID: String, accepted: Int?): Single<Int> {
        return repository.updateAcceptedEnergyElectricSection(sectionID, accepted)
    }
}