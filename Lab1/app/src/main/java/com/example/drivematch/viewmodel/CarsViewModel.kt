package com.example.drivematch.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drivematch.data.CarRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException


sealed interface CarsEvent {
    data object NotImplemented : CarsEvent
}

class CarsViewModel(
    private val repository: CarRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CarsUiState())
    val uiState: StateFlow<CarsUiState> = _uiState.asStateFlow()

    private val _events = Channel<CarsEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    private var loadJob: Job? = null
    init {
        loadCars()
    }

    fun loadCars() {
        if (loadJob?.isActive == true) return
        _uiState.update { it.copy(isLoading = true, isError = false) }
        loadJob = viewModelScope.launch {
            try {
                val cars = repository.getCars()
                _uiState.update { it.copy(cars = cars, isLoading = false) }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e("CarsViewModel", "load failed", e)
                _uiState.update { it.copy(isLoading = false, isError = true) }
            }
        }
    }

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

    fun onNotImplementedClick() {
        _events.trySend(CarsEvent.NotImplemented)
    }
}