package com.example.myfirstapp.domain.usecase.locomotive

import com.example.myfirstapp.domain.entity.CountSections
import com.example.myfirstapp.domain.repository.IRepository
import io.reactivex.rxjava3.core.Single

class UpdateCountSectionUseCase(private val repository: IRepository) {
    fun execute(locomotiveDataId: String, countSections: CountSections): Single<Int> {
        return repository.updateCountSection(locomotiveDataId, countSections)
    }
}