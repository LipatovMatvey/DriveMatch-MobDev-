package com.example.drivematch.viewmodel

import androidx.compose.runtime.Stable
import com.example.drivematch.model.Rental
import com.example.drivematch.model.RentalItem

@Stable
data class RentalsUiState(
    val items: List<RentalItem> = emptyList()
)