package com.example.drivematch.data

import com.example.drivematch.data.remote.CreateRentalRequest
import com.example.drivematch.data.remote.RentalApi
import com.example.drivematch.data.remote.toModel
import com.example.drivematch.model.Rental
import com.example.drivematch.model.RentalItem

class NetworkRentalRepository(
    private val api: RentalApi,
    private val carRepository: CarRepository
) : RentalRepository {

    override suspend fun getRentals(): List<Rental> =
        api.getRentals().map { it.toModel() }

    override suspend fun createRental(carId: Int, hours: Int): Rental =
        api.createRental(CreateRentalRequest(carId, hours)).toModel()

    override suspend fun getRentalItems(): List<RentalItem> {
        val rentals = getRentals()
        if (rentals.isEmpty()) return emptyList()
        val carsById = carRepository.getCars().associateBy { it.id }
        return rentals
            .mapNotNull { rental ->
                carsById[rental.carId]?.let { car -> RentalItem(rental, car) }
            }
            .asReversed()
    }
}