package com.example.drivematch.viewmodel

import androidx.compose.runtime.Stable
import com.example.drivematch.model.User

@Stable
data class ProfileUiState(
    val profile: User? = null,
    val isLoading: Boolean = true,
    val isError: Boolean = false
)