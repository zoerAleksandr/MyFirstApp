package com.example.myfirstapp.Repository

import com.example.myfirstapp.data.Itinerary

interface Repository {
    fun getDataFromLocal(): MutableList<Itinerary>
    fun getDataFromServer(): List<Itinerary>
    fun addData()
    fun remove(position: Int)
    fun getSize(): Int
    fun getItinerary(position: Int): Itinerary
}