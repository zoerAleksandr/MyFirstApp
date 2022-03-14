package com.example.myfirstapp.Repository

import com.example.myfirstapp.data.Itinerary

// Должен быть отдельный метод по созданию объекта и отдельный по заполнению необязательными данными

class RepositoryImpl : Repository {

    companion object {
        fun newInstance() = RepositoryImpl()
    }

    private var data: MutableList<Itinerary> = mutableListOf()

    override fun getDataFromLocal() = data

    override fun getDataFromServer(): List<Itinerary> {
        TODO("Not yet implemented")
    }

    override fun addData(itinerary: Itinerary) {
        data.add(itinerary)
    }

    override fun remove(position: Int) {
        data.removeAt(position)
    }

    override fun getSize(): Int {
        return data.size
    }

    override fun getItinerary(position: Int) = data[position]
}