package com.example.drivematch.model

data class Rental(
    val id: Int,
    val carId: Int,
    val hours: Int,
    val totalPrice: Int
)