package com.example.drivematch.data

import com.example.drivematch.model.User

interface UserRepository {

    /**
     * Получение пользователя
     */
    fun getUser(): User
}

object InMemoryUserRepository : UserRepository {
    override fun getUser(): User = sampleUser1
}