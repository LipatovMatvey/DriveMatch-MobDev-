package com.example.drivematch.data

import com.example.drivematch.model.Rental
import com.example.drivematch.model.RentalItem

interface RentalRepository {

    /**
     * Получение списка всех арендованных автомобилей
     */
    suspend fun getRentals(): List<Rental>

    /**
     * Создание аренды
     */
    suspend fun createRental(carId: Int, hours: Int): Rental

    /**
     * Список аренд вместе с данными автомобилей
     */
    suspend fun getRentalItems(): List<RentalItem>
}

object InMemoryRentalRepository : RentalRepository {
    private val rentals = mutableListOf<Rental>()

    override suspend fun getRentals(): List<Rental> = rentals.toList()

    override suspend fun createRental(carId: Int, hours: Int): Rental {
        val car = carsData.first {it.id == carId}
        val rental = Rental(
            id = rentals.size + 1,
            carId = carId,
            hours = hours,
            totalPrice = car.priceFor(hours)
        )
        rentals.add(rental)
        return rental
    }

    override suspend fun getRentalItems(): List<RentalItem> {
        val carsById = carsData.associateBy { it.id }
        return rentals
            .mapNotNull { rental ->
                carsById[rental.carId]?.let { car -> RentalItem(rental, car) }
            }
            .asReversed()
    }
}