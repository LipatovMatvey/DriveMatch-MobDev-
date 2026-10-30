package com.example.drivematch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.drivematch.data.CarRepository

class CarsViewModelFactory(
    private val repository: CarRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass.isAssignableFrom(CarsViewModel::class.java)) {
            "Unknown ViewModel class: ${modelClass.name}"
        }
        return CarsViewModel(repository) as T
    }
}