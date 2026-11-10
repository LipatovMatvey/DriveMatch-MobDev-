package com.example.drivematch.data

import com.example.drivematch.model.User

interface UserRepository {
    fun getProfile(): User
}

object InMemoryUserRepository : UserRepository {
    override fun getProfile(): User = sampleUser1
}