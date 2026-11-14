package com.example.drivematch.data

import com.example.drivematch.model.User

interface UserRepository {

    /**
     * Получение пользователя
     */
    suspend fun getUser(): User
}

object InMemoryUserRepository : UserRepository {
    override suspend fun getUser(): User = sampleUser1
}