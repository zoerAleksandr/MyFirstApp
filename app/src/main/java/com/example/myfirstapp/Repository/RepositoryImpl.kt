package com.example.myfirstapp.Repository

import com.example.myfirstapp.data.Itinerary

class RepositoryImpl : Repository {

    companion object {
        fun newInstance() = RepositoryImpl()
    }

    private var data: MutableList<Itinerary> = mutableListOf()

    override fun getDataFromLocal() = data

    override fun getDataFromServer(): List<Itinerary> {
        TODO("Not yet implemented")
    }

    override fun getNewData(): MutableList<Itinerary> {
        data = mutableListOf(
            Itinerary(),
            Itinerary(),
            Itinerary()
        )
        return data
    }

    override fun addData(itinerary: Itinerary): Itinerary {
        data.add(itinerary)
        return itinerary
    }

    override fun remove(position: Int) {
        data.removeAt(position)
    }

    override fun getSize(): Int {
        return data.size
    }
}