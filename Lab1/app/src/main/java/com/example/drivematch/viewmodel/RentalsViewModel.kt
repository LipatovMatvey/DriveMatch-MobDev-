package com.example.drivematch.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drivematch.data.CarRepository
import com.example.drivematch.data.RentalRepository
import com.example.drivematch.model.Car
import com.example.drivematch.model.RentalItem
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

sealed interface RentalEvent {
    data object Created : RentalEvent
    data object Failed : RentalEvent
}

class RentalsViewModel(
    private val rentalRepository: RentalRepository,
    private val carRepository: CarRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RentalsUiState())
    val uiState: StateFlow<RentalsUiState> = _uiState.asStateFlow()

    private val _events = Channel<RentalEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    init {
        loadRentals()
    }

    fun loadRentals() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(items = loadItems()) }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e("RentalsViewModel", "load failed", e)
            }
        }
    }

    fun onConfirmRent() {
        val state = _uiState.value
        val car = state.selectedCar ?: return
        if (state.isSubmitting) return
        _uiState.update { it.copy(isSubmitting = true) }
        viewModelScope.launch {
            try {
                val rental = rentalRepository.createRental(car.id, state.hours)
                _uiState.update { current ->
                    current.copy(items = listOf(RentalItem(rental, car)) + current.items)
                }
                _events.trySend(RentalEvent.Created)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e("RentalsViewModel", "create failed", e)
                _events.trySend(RentalEvent.Failed)
            } finally {
                _uiState.update { it.copy(isSubmitting = false) }
            }
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

    fun selectCar(car: Car) {
        _uiState.update {
            it.copy(selectedCar = car, hours = MIN_RENT_HOURS)
        }
    }

    fun onIncreaseHours() {
        _uiState.update { state ->
            if (state.hours >= MAX_RENT_HOURS) state
            else state.copy(hours = state.hours + 1)
        }
    }

    fun onDecreaseHours() {
        _uiState.update { state ->
            if (state.hours <= MIN_RENT_HOURS) state
            else state.copy(hours = state.hours - 1)
        }
    }

}