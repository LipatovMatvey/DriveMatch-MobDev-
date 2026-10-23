package com.example.drivematch.data

import com.example.drivematch.model.Car

interface CarRepository {
    fun getCars(): List<Car>
}

object InMemoryCarRepository : CarRepository {
    override fun getCars(): List<Car> = carsData
}