package com.example.drivematch.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class CarApi(
    private val client: HttpClient,
    private val baseUrl: String
) {
    suspend fun getCars(): List<CarDto> = client.get("$baseUrl/cars").body()
}