package com.example.myfirstapp.domain.usecase.section.electric

import com.example.myfirstapp.domain.entity.ElectricSection
import com.example.myfirstapp.domain.repository.IRepository
import io.reactivex.rxjava3.core.Single

class GetElectricSectionUseCaseList(private val repository: IRepository) {
    fun execute(locomotiveDataID: String): Single<List<ElectricSection>>{
        return repository.getElectricSectionList(locomotiveDataID)
    }
}