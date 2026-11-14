package com.example.drivematch.data

import com.example.drivematch.model.User

interface UserRepository {

    /**
     * Получение пользователя
     */
    suspend fun getUser(): User

    suspend fun updateUser(user: User): User
}

object InMemoryUserRepository : UserRepository {
    private var profile = sampleUser1
    override suspend fun getUser(): User = profile
    override suspend fun updateUser(user: User): User = user.also { profile = it }
}