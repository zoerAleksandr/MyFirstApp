package com.example.myfirstapp.domain.usecase.section.diesel

import com.example.myfirstapp.domain.repository.IRepository
import io.reactivex.rxjava3.core.Single

class UpdateAcceptedDieselFuelSectionUseCase(val repository: IRepository) {
    fun execute(sectionID: String, accepted: Int?): Single<Int> {
        return repository.updateAcceptedDieselFuelSection(sectionID, accepted)
    }
}