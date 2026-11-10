package com.example.drivematch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.drivematch.data.RentalRepository

class RentalsViewModelFactory(
    private val repository: RentalRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass.isAssignableFrom(RentalsViewModel::class.java)) {
            "Unknown ViewModel class: ${modelClass.name}"
        }
        return RentalsViewModel(repository) as T
    }
}