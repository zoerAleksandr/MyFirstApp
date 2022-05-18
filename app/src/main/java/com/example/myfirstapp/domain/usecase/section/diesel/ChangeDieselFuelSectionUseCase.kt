package com.example.myfirstapp.domain.usecase.section.diesel

import com.example.myfirstapp.domain.entity.DieselFuelSection
import com.example.myfirstapp.domain.repository.IRepository

class ChangeDieselFuelSectionUseCase(val repository: IRepository) {
    fun execute(dieselFuelSection: DieselFuelSection){
        repository.changeDieselFuelSection(dieselFuelSection)
    }
}