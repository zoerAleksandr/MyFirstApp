package com.example.myfirstapp.domain.repository

import com.example.myfirstapp.domain.entity.LocomotiveData

interface IRepositoryLocomotive {
    fun getLocomotiveData(locomotiveDataID: String): LocomotiveData
    fun getListLocomotiveData(itineraryID: String): List<LocomotiveData>
    fun addLocomotiveData(locomotiveData: LocomotiveData): Long
    fun deleteLocomotiveData(locomotiveData: LocomotiveData): Int
    fun updateLocomotiveData(locomotiveData: LocomotiveData): Int
}