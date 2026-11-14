package com.example.drivematch.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drivematch.data.UserRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class ProfileViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private var loadJob: Job? = null

    init {
        loadProfile()
    }

    fun loadProfile() {
        if (loadJob?.isActive == true) return
        _uiState.update { it.copy(isLoading = true, isError = false) }
        loadJob = viewModelScope.launch {
            try {
                val profile = repository.getUser()
                _uiState.update { it.copy(profile = profile, isLoading = false) }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "load failed", e)
                _uiState.update { it.copy(isLoading = false, isError = true) }
            }
        }
    }
}