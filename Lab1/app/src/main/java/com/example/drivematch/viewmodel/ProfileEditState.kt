package com.example.drivematch.viewmodel

import androidx.compose.runtime.Stable
import com.example.drivematch.model.ProfileError
import com.example.drivematch.model.ProfileField

@Stable
data class ProfileEditState(
    val fullName: String = "",
    val birthDate: String = "",
    val city: String = "",
    val licenseCategory: String = "",
    val licenseNumber: String = "",
    val licenseValidUntil: String = "",
    val errors: Map<ProfileField, ProfileError> = emptyMap(),
    val isValid: Boolean = false
)
