package com.example.drivematch.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drivematch.data.UserRepository
import com.example.drivematch.model.User
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

sealed interface ProfileEvent {
    data object Saved : ProfileEvent
    data object SaveFailed : ProfileEvent
}

class ProfileViewModel(
    private val repository: UserRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private val _events = Channel<ProfileEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    fun onSave(user: User) {
        if (_uiState.value.isSaving) return
        _uiState.update { it.copy(isSaving = true) }
        viewModelScope.launch {
            try {
                val saved = repository.updateUser(user)
                _uiState.update { it.copy(profile = saved) }
                _events.trySend(ProfileEvent.Saved)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "save failed", e)
                _events.trySend(ProfileEvent.SaveFailed)
            } finally {
                _uiState.update { it.copy(isSaving = false) }
            }
        }
    }

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