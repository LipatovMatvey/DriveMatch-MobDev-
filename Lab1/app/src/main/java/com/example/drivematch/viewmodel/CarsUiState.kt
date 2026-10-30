package com.example.drivematch.viewmodel

import com.example.drivematch.model.Car

data class CarsUiState(
    val cars: List<Car> = emptyList(),
    val favouriteIds: Set<Int> = emptySet()
) {
    val favouriteCars: List<Car>
        get() = cars.filter { it.id in favouriteIds }

    fun carById(id: Int?): Car? = cars.firstOrNull { it.id == id }
}