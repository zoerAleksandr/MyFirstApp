package com.example.myfirstapp.domain.usecase.section.electric

import com.example.myfirstapp.domain.entity.ElectricSection
import com.example.myfirstapp.domain.repository.IRepository
import io.reactivex.rxjava3.core.Single

class GetElectricSectionUseCase(private val repository: IRepository) {
    fun execute(sectionID: String): Single<ElectricSection>{
        return repository.getElectricSection(sectionID)
    }
}