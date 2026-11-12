package com.example.drivematch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.drivematch.data.CarRepository
import com.example.drivematch.data.RentalRepository

class RentalsViewModelFactory(
    private val rentalRepository: RentalRepository,
    private val carRepository: CarRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass.isAssignableFrom(RentalsViewModel::class.java)) {
            "Unknown ViewModel class: ${modelClass.name}"
        }
        return RentalsViewModel(rentalRepository, carRepository) as T
    }
}