package com.example.myfirstapp.data.room.repository

import com.example.myfirstapp.data.room.ItineraryDAO
import com.example.myfirstapp.domain.entity.Station
import com.example.myfirstapp.domain.repository.IRepositoryStation
import com.example.myfirstapp.utils.convert_entity.toStationRoomEntity
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RoomRepositoryStation : IRepositoryStation, KoinComponent {
    private val localStorage: ItineraryDAO by inject()

    override fun addStation(station: Station): Long {
        return localStorage.addStation(
            toStationRoomEntity(station)
        )
    }

    override fun getStation(stationId: String): Station {
        TODO("Not yet implemented")
    }

    override fun getListStation(trainDataID: String): MutableList<Station> {
        TODO("Not yet implemented")
    }

    override fun deleteStation(station: Station): Int {
        TODO("Not yet implemented")
    }

    override fun updateStation(station: Station): Int {
        TODO("Not yet implemented")
    }
}