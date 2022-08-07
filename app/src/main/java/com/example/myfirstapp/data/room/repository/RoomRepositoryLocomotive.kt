package com.example.myfirstapp.data.room.repository

import com.example.myfirstapp.data.room.ItineraryDAO
import com.example.myfirstapp.domain.entity.LocomotiveData
import com.example.myfirstapp.domain.repository.IRepositoryLocomotive
import com.example.myfirstapp.utils.convert_entity.toLocomotiveDataRoomEntity
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RoomRepositoryLocomotive : IRepositoryLocomotive, KoinComponent {
    private val localStorage: ItineraryDAO by inject()

    override fun getLocomotiveData(locomotiveDataID: String): LocomotiveData {
        TODO("Not yet implemented")
    }

    override fun getListLocomotiveData(itineraryID: String): List<LocomotiveData> {
        TODO("Not yet implemented")
    }

    override fun addLocomotiveData(locomotiveData: LocomotiveData): Long {
        return localStorage.addLocomotiveData(
            toLocomotiveDataRoomEntity(locomotiveData)
        )
    }

    override fun deleteLocomotiveData(locomotiveData: LocomotiveData): Int {
        TODO("Not yet implemented")
    }

    override fun updateLocomotiveData(locomotiveData: LocomotiveData): Int {
        TODO("Not yet implemented")
    }
}