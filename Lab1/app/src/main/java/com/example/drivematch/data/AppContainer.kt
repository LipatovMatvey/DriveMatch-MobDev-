package com.example.drivematch.data

import com.example.drivematch.data.remote.BASE_URL
import com.example.drivematch.data.remote.CarApi
import com.example.drivematch.data.remote.createHttpClient

object AppContainer {
    private val httpClient by lazy { createHttpClient() }

    val carRepository: CarRepository by lazy {
        NetworkCarRepository(CarApi(httpClient, BASE_URL), BASE_URL)
    }
}