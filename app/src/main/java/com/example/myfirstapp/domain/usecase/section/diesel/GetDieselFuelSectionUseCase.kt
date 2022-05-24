package com.example.myfirstapp.domain.usecase.section.diesel

import com.example.myfirstapp.domain.entity.DieselFuelSection
import com.example.myfirstapp.domain.repository.IRepository
import io.reactivex.rxjava3.core.Single

class GetDieselFuelSectionUseCase(private val repository: IRepository) {
    fun execute(sectionID: String): Single<DieselFuelSection>{
        return repository.getDieselFuelSection(sectionID)
    }
}