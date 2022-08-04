package com.example.myfirstapp.data.room.repository

import com.example.myfirstapp.domain.entity.Station
import com.example.myfirstapp.domain.repository.IRepositoryStation
import io.reactivex.rxjava3.core.Single

class RoomRepositoryStation : IRepositoryStation {
    override fun addStation(station: Station): Long {
        TODO("Not yet implemented")
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