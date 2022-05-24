package com.example.myfirstapp.domain.usecase.section.diesel

import com.example.myfirstapp.domain.entity.DieselFuelSection
import com.example.myfirstapp.domain.repository.IRepository
import io.reactivex.rxjava3.core.Single

class ChangeDieselFuelSectionUseCase(val repository: IRepository) {
    fun execute(dieselFuelSection: DieselFuelSection): Single<Int> {
       return repository.changeDieselFuelSection(dieselFuelSection)
    }
}