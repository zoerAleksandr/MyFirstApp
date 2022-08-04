package com.example.myfirstapp.domain.usecase.locomotive

import com.example.myfirstapp.domain.entity.LocomotiveData
import com.example.myfirstapp.domain.repository.IRepositoryLocomotive

class AddLocomotiveDataUseCase(private val repository: IRepositoryLocomotive) {
    fun execute(locomotiveData: LocomotiveData): Long {
        return repository.addLocomotiveData(locomotiveData)
    }
}