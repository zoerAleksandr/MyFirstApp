package com.example.myfirstapp.domain.usecase.section.diesel

import com.example.myfirstapp.domain.entity.DieselFuelSection
import com.example.myfirstapp.domain.repository.IRepository
import io.reactivex.rxjava3.core.Single

class UpdateDeliveryDieselFuelSectionUseCase(val repository: IRepository) {
    fun execute(sectionID: String, delivery: Int?): Single<Int> {
       return repository.updateDeliveryDieselFuelSection(sectionID, delivery)
    }
}