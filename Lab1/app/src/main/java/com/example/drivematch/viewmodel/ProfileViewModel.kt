package com.example.drivematch.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drivematch.data.UserRepository
import com.example.drivematch.model.ProfileField
import com.example.drivematch.model.hasBlankFields
import com.example.drivematch.model.normalized
import com.example.drivematch.model.validate
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

sealed interface ProfileEvent {
    data object Saved : ProfileEvent
    data object SaveFailed : ProfileEvent
    data object BalanceToppedUp : ProfileEvent
    data object TopUpFailed : ProfileEvent
}

const val TOP_UP_AMOUNT = 100

class ProfileViewModel(
    private val repository: UserRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private val _events = Channel<ProfileEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

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

    private var topUpJob: Job? = null

    fun onTopUp() {
        if (topUpJob?.isActive == true) return
        topUpJob = viewModelScope.launch {
            try {
                val updated = repository.topUp(TOP_UP_AMOUNT)
                _uiState.update { state ->
                    state.copy(profile = state.profile?.copy(balance = updated.balance))
                }
                _events.trySend(ProfileEvent.BalanceToppedUp)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "top up failed", e)
                _events.trySend(ProfileEvent.TopUpFailed)
            }
        }
    }

    fun startEditing() {
        val profile = _uiState.value.profile ?: return
        val errors = profile.validate()
        _uiState.update {
            it.copy(
                editState = ProfileEditState(
                    fullName = profile.fullName,
                    birthDate = profile.birthDate,
                    city = profile.city,
                    licenseCategory = profile.licenseCategory,
                    licenseNumber = profile.licenseNumber,
                    licenseValidUntil = profile.licenseValidUntil,
                    errors = errors,
                    isValid = errors.isEmpty() && !profile.hasBlankFields()
                )
            )
        }
    }

    fun onFieldChange(field: ProfileField, value: String) {
        val current = _uiState.value.editState ?: return
        val updated = when (field) {
            ProfileField.FULL_NAME -> current.copy(fullName = value)
            ProfileField.CITY -> current.copy(city = value)
            ProfileField.LICENSE_CATEGORY -> current.copy(licenseCategory = value)
            ProfileField.LICENSE_NUMBER -> current.copy(licenseNumber = value)
            ProfileField.LICENSE_VALID_UNTIL -> current.copy(licenseValidUntil = value)
        }
        val draftUser = _uiState.value.profile?.copy(
            fullName = updated.fullName.trim(),
            city = updated.city.trim(),
            licenseCategory = updated.licenseCategory.trim(),
            licenseNumber = updated.licenseNumber.trim(),
            licenseValidUntil = updated.licenseValidUntil.trim()
        )
        val errors = draftUser?.validate() ?: emptyMap()
        val isValid = errors.isEmpty() && draftUser?.hasBlankFields() == false
        _uiState.update { it.copy(editState = updated.copy(errors = errors, isValid = isValid)) }
    }

    fun onSave() {
        val editState = _uiState.value.editState ?: return
        val currentProfile = _uiState.value.profile ?: return
        if (!editState.isValid || _uiState.value.isSaving) return
        val user = currentProfile.copy(
            fullName = editState.fullName.trim(),
            city = editState.city.trim(),
            licenseCategory = editState.licenseCategory.trim(),
            licenseNumber = editState.licenseNumber.trim(),
            licenseValidUntil = editState.licenseValidUntil.trim()
        )
        _uiState.update { it.copy(isSaving = true) }
        viewModelScope.launch {
            try {
                val saved = repository.updateUser(user.normalized())
                _uiState.update { it.copy(profile = saved, editState = null) }
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
}