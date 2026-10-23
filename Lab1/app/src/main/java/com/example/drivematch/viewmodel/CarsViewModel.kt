package com.example.drivematch.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.drivematch.data.CarRepository
import com.example.drivematch.data.InMemoryCarRepository
import com.example.drivematch.model.Car
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

sealed interface CarsEvent {
    data object NotImplemented : CarsEvent
}

class CarsViewModel : ViewModel() {

    private val repository: CarRepository = InMemoryCarRepository
    val cars: List<Car> = repository.getCars()

    var favouriteIds by mutableStateOf(setOf<Int>())
        private set

    private val _events = Channel<CarsEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    fun onToggleFavourite(id: Int) {
        favouriteIds = if (id in favouriteIds) favouriteIds - id else favouriteIds + id
    }

    fun onRentClick() {
        _events.trySend(CarsEvent.NotImplemented)
    }

}