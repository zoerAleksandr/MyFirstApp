package com.example.myfirstapp.domain.usecase.locomotive

import com.example.myfirstapp.domain.entity.LocomotiveData
import com.example.myfirstapp.domain.repository.IRepository
import io.reactivex.rxjava3.core.Single

class GetLocomotiveDataByIdUseCase(val repository: IRepository) {
    fun execute(idLocoData: String): Single<LocomotiveData>{
        return repository.getLocomotiveData(idLocoData)
    }
}