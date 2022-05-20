package com.example.myfirstapp.domain.usecase.section.diesel

import com.example.myfirstapp.domain.repository.IRepository
import io.reactivex.rxjava3.core.Single

class UpdateConsumptionDieselFuelUseCase(private val repository: IRepository) {
    fun execute(sectionID: String, consumption: Int?): Single<Int>{
       return repository.updateConsumptionDieselFuelSection(
            sectionID, consumption
        )
    }
}