package com.example.drivematch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drivematch.data.CarRepository
import com.example.drivematch.data.RentalRepository
import com.example.drivematch.model.RentalItem
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface RentalEvent {
    data object Created : RentalEvent
}

class RentalsViewModel(
    private val rentalRepository: RentalRepository,
    private val carRepository: CarRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RentalsUiState())
    val uiState: StateFlow<RentalsUiState> = _uiState.asStateFlow()

    private val _events = Channel<RentalEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    fun onConfirmRent(carId: Int, hours: Int) {
        viewModelScope.launch {
            rentalRepository.createRental(carId, hours)
            _uiState.update { it.copy(items = loadItems()) }
            _events.trySend(RentalEvent.Created)
        }
    }

    private suspend fun loadItems(): List<RentalItem> {
        val rentals = rentalRepository.getRentals()
        if (rentals.isEmpty()) return emptyList()
        val carsById = carRepository.getCars().associateBy { it.id }
        return rentals
            .mapNotNull { rental ->
                carsById[rental.carId]?.let { car -> RentalItem(rental, car) }
            }
            .asReversed()
    }
}