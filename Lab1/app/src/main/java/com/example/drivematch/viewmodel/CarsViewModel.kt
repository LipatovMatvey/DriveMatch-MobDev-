package com.example.drivematch.viewmodel

import androidx.lifecycle.ViewModel
import com.example.drivematch.data.CarRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

sealed interface CarsEvent {
    data object NotImplemented : CarsEvent
}

class CarsViewModel(
    repository: CarRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CarsUiState(cars = repository.getCars()))
    val uiState: StateFlow<CarsUiState> = _uiState.asStateFlow()

    private val _events = Channel<CarsEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    fun onToggleFavourite(id: Int) {
        _uiState.update { state ->
            val newIds = if (id in state.favouriteIds) {
                state.favouriteIds - id
            } else {
                state.favouriteIds + id
            }
            state.copy(favouriteIds = newIds)
        }
    }

    fun onRentClick() {
        _events.trySend(CarsEvent.NotImplemented)
    }
}