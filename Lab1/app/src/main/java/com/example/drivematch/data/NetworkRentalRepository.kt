package com.example.drivematch.data

import com.example.drivematch.data.remote.CreateRentalRequest
import com.example.drivematch.data.remote.RentalApi
import com.example.drivematch.data.remote.toModel
import com.example.drivematch.model.Rental

class NetworkRentalRepository(
    private val api: RentalApi
) : RentalRepository {

    override suspend fun getRentals(): List<Rental> =
        api.getRentals().map { it.toModel() }

    override suspend fun createRental(carId: Int, hours: Int): Rental =
        api.createRental(CreateRentalRequest(carId, hours)).toModel()
}