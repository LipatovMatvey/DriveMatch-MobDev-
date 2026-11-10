package com.example.drivematch.data

import com.example.drivematch.model.Car

interface CarRepository {

    /**
     * Получение списка всех автомобилей
     */
    fun getCars(): List<Car>
}

object InMemoryCarRepository : CarRepository {
    override fun getCars(): List<Car> = carsData
}