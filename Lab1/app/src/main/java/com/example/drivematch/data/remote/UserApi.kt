package com.example.drivematch.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class UserApi(
    private val client: HttpClient,
    private val baseUrl: String
) {
    suspend fun getUser(): UserDto = client.get("$baseUrl/profile").body()

    suspend fun updateUser(dto: UserDto): UserDto =
        client.put("$baseUrl/profile") {
            contentType(ContentType.Application.Json)
            setBody(dto)
        }.body()
}