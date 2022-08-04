package com.example.myfirstapp.data.room.repository

import com.example.myfirstapp.domain.entity.LocomotiveData
import com.example.myfirstapp.domain.repository.IRepositoryLocomotive
import io.reactivex.rxjava3.core.Single

class RoomRepositoryLocomotive : IRepositoryLocomotive {
    override fun getLocomotiveData(locomotiveDataID: String): LocomotiveData {
        TODO("Not yet implemented")
    }

    override fun getListLocomotiveData(itineraryID: String): List<LocomotiveData> {
        TODO("Not yet implemented")
    }

    override fun addLocomotiveData(locomotiveData: LocomotiveData): Long {
        TODO("Not yet implemented")
    }

    override fun deleteLocomotiveData(locomotiveData: LocomotiveData): Int {
        TODO("Not yet implemented")
    }

    override fun updateLocomotiveData(locomotiveData: LocomotiveData): Int {
        TODO("Not yet implemented")
    }
}