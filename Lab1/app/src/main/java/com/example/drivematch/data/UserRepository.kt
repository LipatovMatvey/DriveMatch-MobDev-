package com.example.drivematch.data

import com.example.drivematch.model.User

interface ProfileRepository {
    fun getProfile(): User
}

object InMemoryProfileRepository : ProfileRepository {
    override fun getProfile(): User = sampleUser1
}