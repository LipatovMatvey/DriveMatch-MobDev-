package com.example.drivematch.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class RentalApi(
    private val client: HttpClient,
    private val baseUrl: String
) {
    suspend fun getRentals(): List<RentalDto> =
        client.get("$baseUrl/rentals").body()

    suspend fun createRental(request: CreateRentalRequest): RentalDto =
        client.post("$baseUrl/rentals") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
}