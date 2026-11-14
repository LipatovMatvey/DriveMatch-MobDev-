package com.example.drivematch.data

import com.example.drivematch.data.remote.UserApi
import com.example.drivematch.data.remote.toModel
import com.example.drivematch.model.User

class NetworkUserRepository(
    private val api: UserApi
) : UserRepository {

    override suspend fun getUser(): User = api.getUser().toModel()
}