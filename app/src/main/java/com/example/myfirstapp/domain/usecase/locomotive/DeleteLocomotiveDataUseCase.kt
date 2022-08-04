package com.example.myfirstapp.domain.usecase.locomotive

import com.example.myfirstapp.domain.entity.LocomotiveData
import com.example.myfirstapp.domain.repository.IRepositoryLocomotive

class DeleteLocomotiveDataUseCase(private val repository: IRepositoryLocomotive) {
    fun execute(locomotiveData: LocomotiveData): Int {
        return repository.deleteLocomotiveData(locomotiveData)
    }
}