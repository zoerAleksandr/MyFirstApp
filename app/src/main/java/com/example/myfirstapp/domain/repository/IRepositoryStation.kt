package com.example.myfirstapp.domain.repository

import com.example.myfirstapp.domain.entity.Station

interface IRepositoryStation {
    fun addStation(station: Station): Long
    fun getStation(stationId: String): Station
    fun getListStation(trainDataID: String): MutableList<Station>
    fun deleteStation(station: Station): Int
    fun updateStation(station: Station): Int
}