package com.example.drivematch.data

import com.example.drivematch.data.remote.CarApi
import com.example.drivematch.data.remote.toModel
import com.example.drivematch.model.Car

class NetworkCarRepository(
    private val api: CarApi,
    private val baseUrl: String
) : CarRepository {

    override suspend fun getCars(): List<Car> =
        api.getCars().map { it.toModel(baseUrl) }
}