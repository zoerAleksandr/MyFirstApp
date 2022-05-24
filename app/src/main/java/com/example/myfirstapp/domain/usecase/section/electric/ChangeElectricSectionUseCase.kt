package com.example.myfirstapp.domain.usecase.section.electric

import com.example.myfirstapp.domain.entity.ElectricSection
import com.example.myfirstapp.domain.repository.IRepository
import io.reactivex.rxjava3.core.Single

class ChangeElectricSectionUseCase(private val repository: IRepository) {
    fun execute(electricSection: ElectricSection): Single<Int>{
        return repository.changeElectricSection(electricSection)
    }
}