package com.example.drivematch.data

import com.example.drivematch.model.Rental

interface RentalRepository {

    /**
     * Получение списка всех арендованных автомобилей
     */
    fun getRentals(): List<Rental>

    /**
     * Создание аренды
     */
    fun createRental(carId: Int, hours: Int): Rental
}

object InMemoryRentalRepository : RentalRepository {
    private val rentals = mutableListOf<Rental>()

    override fun getRentals(): List<Rental> = rentals.toList()

    override fun createRental(carId: Int, hours: Int): Rental {
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
}