package com.example.myfirstapp.Repository

import com.example.myfirstapp.data.Itinerary

interface Repository {
    fun getDataFromLocal(): MutableList<Itinerary>
    fun getDataFromServer(): List<Itinerary>
    fun getNewData(): List<Itinerary>
    fun addData(itinerary: Itinerary): Itinerary
    fun remove(position: Int)
    fun getSize(): Int
}