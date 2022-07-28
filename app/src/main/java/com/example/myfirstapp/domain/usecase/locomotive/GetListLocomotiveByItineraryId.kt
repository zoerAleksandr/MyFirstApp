package com.example.myfirstapp.domain.usecase.locomotive

import com.example.myfirstapp.domain.entity.LocomotiveData
import com.example.myfirstapp.domain.repository.IRepository
import io.reactivex.rxjava3.core.Single

class GetListLocomotiveByItineraryId(private val repository: IRepository) {
    fun execute(itineraryId: String): Single<List<LocomotiveData>>{
        return repository.getListLocomotiveData(itineraryId)
    }
}