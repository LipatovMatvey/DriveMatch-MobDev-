package com.example.drivematch.viewmodel

import androidx.compose.runtime.Stable
import com.example.drivematch.model.Car

@Stable
data class CarsUiState(
    val cars: List<Car> = emptyList(),
    val favouriteIds: Set<Int> = emptySet(),
    val isLoading: Boolean = true,
    val isError: Boolean = false
) {
    val favouriteCars: List<Car>
        get() = cars.filter { it.id in favouriteIds }

    fun carById(id: Int?): Car? = cars.firstOrNull { it.id == id }
}