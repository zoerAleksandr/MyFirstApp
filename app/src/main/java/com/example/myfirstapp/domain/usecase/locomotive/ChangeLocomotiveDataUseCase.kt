package com.example.myfirstapp.domain.usecase.locomotive

import com.example.myfirstapp.domain.entity.LocomotiveData
import com.example.myfirstapp.domain.repository.IRepository

class ChangeLocomotiveDataUseCase(val repository: IRepository) {
    fun execute(locomotiveData: LocomotiveData){
        repository.changeLocomotiveData(locomotiveData)
    }
}