package com.example.drivematch.viewmodel

import androidx.compose.runtime.Stable
import com.example.drivematch.model.Car
import com.example.drivematch.model.RentalItem

const val MIN_RENT_HOURS = 1
const val MAX_RENT_HOURS = 24

@Stable
data class RentalsUiState(
    val items: List<RentalItem> = emptyList(),
    val isSubmitting: Boolean = false,
    val selectedCar: Car? = null,
    val hours: Int = MIN_RENT_HOURS,
) {
    val totalPrice: Int
        get() = selectedCar?.priceFor(hours) ?: 0

    val canDecreaseHours: Boolean
        get() = hours > MIN_RENT_HOURS

    val canIncreaseHours: Boolean
        get() = hours < MAX_RENT_HOURS
}