package com.example.drivematch.data

import com.example.drivematch.data.remote.BASE_URL
import com.example.drivematch.data.remote.CarApi
import com.example.drivematch.data.remote.RentalApi
import com.example.drivematch.data.remote.UserApi
import com.example.drivematch.data.remote.createHttpClient

object AppContainer {
    private val httpClient by lazy { createHttpClient() }

    val carRepository: CarRepository by lazy {
        NetworkCarRepository(CarApi(httpClient, BASE_URL), BASE_URL)
    }

    val userRepository: UserRepository by lazy {
        NetworkUserRepository(UserApi(httpClient, BASE_URL))
    }

    val rentalRepository: RentalRepository by lazy {
        NetworkRentalRepository(RentalApi(httpClient, BASE_URL))
    }
}