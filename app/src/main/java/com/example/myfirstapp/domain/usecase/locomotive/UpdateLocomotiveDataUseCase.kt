package com.example.myfirstapp.domain.usecase.locomotive

import com.example.myfirstapp.domain.entity.LocomotiveData
import com.example.myfirstapp.domain.repository.IRepositoryLocomotive

class UpdateLocomotiveDataUseCase(val repository: IRepositoryLocomotive) {
    fun execute(locomotiveData: LocomotiveData): Int {
        return repository.updateLocomotiveData(locomotiveData)
    }
}