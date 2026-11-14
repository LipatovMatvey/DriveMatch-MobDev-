package com.example.drivematch.data.remote

import com.example.drivematch.model.Rental
import kotlinx.serialization.Serializable

@Serializable
data class RentalDto(
    val id: Int,
    val carId: Int,
    val hours: Int,
    val totalPrice: Int
)

fun RentalDto.toModel(): Rental = Rental(
    id = id,
    carId = carId,
    hours = hours,
    totalPrice = totalPrice
)

@Serializable
data class CreateRentalRequest(
    val carId: Int,
    val hours: Int
)