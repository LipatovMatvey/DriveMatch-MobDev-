package com.example.drivematch.viewmodel

import androidx.compose.runtime.Stable
import com.example.drivematch.model.Rental

@Stable
data class RentalsUiState(
    val rentals: List<Rental> = emptyList()
)