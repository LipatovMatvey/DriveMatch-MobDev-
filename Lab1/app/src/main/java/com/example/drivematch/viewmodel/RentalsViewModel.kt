package com.example.drivematch.viewmodel

import androidx.lifecycle.ViewModel
import com.example.drivematch.data.RentalRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

sealed interface RentalEvent {
    data object Created : RentalEvent
}

class RentalsViewModel(
    private val repository: RentalRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RentalsUiState(rentals = repository.getRentals()))
    val uiState: StateFlow<RentalsUiState> = _uiState.asStateFlow()

    private val _events = Channel<RentalEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    fun onConfirmRent(carId: Int, hours: Int) {
        repository.createRental(carId, hours)
        _uiState.update { it.copy(rentals = repository.getRentals()) }
        _events.trySend(RentalEvent.Created)
    }
}