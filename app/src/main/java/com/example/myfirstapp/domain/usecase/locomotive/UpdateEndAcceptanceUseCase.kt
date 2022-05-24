package com.example.myfirstapp.domain.usecase.locomotive

import com.example.myfirstapp.domain.repository.IRepository
import io.reactivex.rxjava3.core.Single
import java.util.*

class UpdateEndAcceptanceUseCase(private val repository: IRepository) {
    fun execute(locomotiveDataId: String, calendar: Calendar?): Single<Int>{
        return repository.updateEndAcceptance(locomotiveDataId, calendar)
    }
}