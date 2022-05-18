package com.example.myfirstapp.domain.usecase.section.diesel

import com.example.myfirstapp.domain.entity.DieselFuelSection
import com.example.myfirstapp.domain.repository.IRepository
import io.reactivex.rxjava3.core.Single

class GetListDieselFuelSectionUseCase(val repository: IRepository) {
    fun execute(locomotiveDataID: String): Single<List<DieselFuelSection>>{
       return repository.getDieselFuelSectionList(locomotiveDataID)
    }
}