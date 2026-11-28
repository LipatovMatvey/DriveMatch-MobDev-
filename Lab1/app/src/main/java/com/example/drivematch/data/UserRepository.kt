package com.example.drivematch.data

import com.example.drivematch.model.User

interface UserRepository {

    /**
     * Получение пользователя
     */
    suspend fun getUser(): User

    /**
     * Обновление пользователя
     */
    suspend fun updateUser(user: User): User

    /**
     * Пополнение баланса
     */
    suspend fun topUp(amount: Int): User
}

object InMemoryUserRepository : UserRepository {
    private var profile = sampleUser1
    override suspend fun getUser(): User = profile
    override suspend fun updateUser(user: User): User = user.also { profile = it }
    override suspend fun topUp(amount: Int): User =
        profile.copy(balance = profile.balance + amount).also { profile = it }
}