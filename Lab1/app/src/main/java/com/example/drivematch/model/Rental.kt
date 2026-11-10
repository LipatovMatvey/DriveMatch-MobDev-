package com.example.drivematch.model

data class Rental(
    /**
     * Идентификатор аренды
     */
    val id: Int,

    /**
     * Идентификатор автомобиля
     */
    val carId: Int,

    /**
     * Количество часов аренды
     */
    val hours: Int,

    /**
     * Стоимость всей аренды
     */
    val totalPrice: Int
)