package com.example.myfirstapp.domain.usecase.section.diesel

import com.example.myfirstapp.domain.entity.DieselFuelSection
import com.example.myfirstapp.domain.repository.IRepository
import io.reactivex.rxjava3.core.Single

class AddDieselFuelSectionUseCase(private val repository: IRepository) {
    fun execute(dieselFuelSection: DieselFuelSection): Single<Long> {
        return repository.addDieselFuelSection(dieselFuelSection)
    }
}