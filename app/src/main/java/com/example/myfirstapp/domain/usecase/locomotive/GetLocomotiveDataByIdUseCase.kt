package com.example.myfirstapp.domain.usecase.locomotive

import com.example.myfirstapp.domain.entity.LocomotiveData
import com.example.myfirstapp.domain.repository.IRepositoryLocomotive

class GetLocomotiveDataByIdUseCase(val repository: IRepositoryLocomotive) {
    fun execute(idLocoData: String): LocomotiveData {
        return repository.getLocomotiveData(idLocoData)
    }
}