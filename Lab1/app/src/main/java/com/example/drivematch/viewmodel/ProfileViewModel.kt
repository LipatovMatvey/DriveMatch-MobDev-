package com.example.drivematch.viewmodel

import androidx.lifecycle.ViewModel
import com.example.drivematch.data.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel(
    repository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState(profile = repository.getProfile()))
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()
}