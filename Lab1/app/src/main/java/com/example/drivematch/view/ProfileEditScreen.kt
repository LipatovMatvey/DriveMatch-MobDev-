package com.example.drivematch.view

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.drivematch.R
import com.example.drivematch.data.sampleUser1
import com.example.drivematch.model.User
import com.example.drivematch.model.hasBlankFields
import com.example.drivematch.ui.theme.AppTheme
import com.example.drivematch.component.ProfileField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileEditScreen(
    profile: User,
    onSave: (User) -> Unit,
    modifier: Modifier = Modifier,
    isSaving: Boolean = false
) {
    var fullName by rememberSaveable { mutableStateOf(profile.fullName) }
    var birthDate by rememberSaveable { mutableStateOf(profile.birthDate) }
    var city by rememberSaveable { mutableStateOf(profile.city) }
    var licenseCategory by rememberSaveable { mutableStateOf(profile.licenseCategory) }
    var licenseNumber by rememberSaveable { mutableStateOf(profile.licenseNumber) }
    var licenseValidUntil by rememberSaveable { mutableStateOf(profile.licenseValidUntil) }

    val draft = profile.copy(
        fullName = fullName.trim(),
        birthDate = birthDate.trim(),
        city = city.trim(),
        licenseCategory = licenseCategory.trim(),
        licenseNumber = licenseNumber.trim(),
        licenseValidUntil = licenseValidUntil.trim()
    )

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.profile_edit_title),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                windowInsets = WindowInsets(0, 0, 0, 0)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ProfileField(R.string.profile_full_name_label, fullName, { fullName = it }, !isSaving)
            ProfileField(R.string.profile_birth_date, birthDate, { birthDate = it }, !isSaving)
            ProfileField(R.string.profile_city, city, { city = it }, !isSaving)
            ProfileField(R.string.profile_license_category, licenseCategory, { licenseCategory = it }, !isSaving)
            ProfileField(R.string.profile_license_number, licenseNumber, { licenseNumber = it }, !isSaving)
            ProfileField(R.string.profile_license_valid_until, licenseValidUntil, { licenseValidUntil = it }, !isSaving)
            Spacer(Modifier.height(8.dp))
            Button(
                onClick = { onSave(draft) },
                enabled = !isSaving && !draft.hasBlankFields(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.profile_save),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Редактирование RU", widthDp = 360, heightDp = 750, locale = "ru")
@Composable
fun ProfileEditScreenPreviewRu() {
    AppTheme { ProfileEditScreen(profile = sampleUser1, onSave = {}) }
}

@Preview(showBackground = true, name = "Редактирование EN", widthDp = 360, heightDp = 750, locale = "en")
@Composable
fun ProfileEditScreenPreviewEn() {
    AppTheme { ProfileEditScreen(profile = sampleUser1, onSave = {}) }
}