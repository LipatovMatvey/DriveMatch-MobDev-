package com.example.drivematch.data

import com.example.drivematch.model.Car

interface CarRepository {
    suspend fun getCars(): List<Car>
}

object InMemoryCarRepository : CarRepository {
    override suspend fun getCars(): List<Car> = carsData
}