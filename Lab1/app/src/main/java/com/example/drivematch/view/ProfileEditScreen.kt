package com.example.drivematch.view

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
import com.example.drivematch.component.ProfileTextField
import com.example.drivematch.data.sampleUser1
import com.example.drivematch.model.ProfileField
import com.example.drivematch.model.User
import com.example.drivematch.model.hasBlankFields
import com.example.drivematch.model.validate
import com.example.drivematch.ui.theme.AppTheme
import com.example.drivematch.viewmodel.ProfileEditState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileEditScreen(
    editState: ProfileEditState,
    isSaving: Boolean,
    onFieldChange: (ProfileField, String) -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
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
            ProfileTextField(
                labelRes = R.string.profile_full_name_label,
                value = editState.fullName,
                onValueChange = { onFieldChange(ProfileField.FULL_NAME, it) },
                enabled = ! isSaving,
                error = editState.errors[ProfileField.FULL_NAME]
            )
            ProfileTextField(
                labelRes = R.string.profile_birth_date,
                value = editState.birthDate,
                onValueChange = {},
                enabled = !isSaving,
                readOnly = true,
                hintRes = R.string.profile_birth_date_readonly
            )
            ProfileTextField(
                labelRes = R.string.profile_city,
                value = editState.city,
                onValueChange = { onFieldChange(ProfileField.CITY, it) },
                enabled = !isSaving,
                error = editState.errors[ProfileField.CITY]
            )
            ProfileTextField(
                labelRes = R.string.profile_license_category,
                value = editState.licenseCategory,
                onValueChange = { onFieldChange(ProfileField.LICENSE_CATEGORY, it) },
                enabled = !isSaving,
                error = editState.errors[ProfileField.LICENSE_CATEGORY]
            )
            ProfileTextField(
                labelRes = R.string.profile_license_number,
                value = editState.licenseNumber,
                onValueChange = { onFieldChange(ProfileField.LICENSE_NUMBER, it) },
                enabled = !isSaving,
                error = editState.errors[ProfileField.LICENSE_NUMBER]
            )
            ProfileTextField(
                labelRes = R.string.profile_license_valid_until,
                value = editState.licenseValidUntil,
                onValueChange = { onFieldChange(ProfileField.LICENSE_VALID_UNTIL, it) },
                enabled = !isSaving,
                error = editState.errors[ProfileField.LICENSE_VALID_UNTIL],
                hintRes = R.string.profile_date_hint
            )
            Spacer(Modifier.height(8.dp))
            Button(
                onClick = onSave,
                enabled = editState.isValid && !isSaving,
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

@Preview(
    showBackground = true,
    name = "Редактирование RU",
    widthDp = 360,
    heightDp = 750,
    locale = "ru"
)
@Composable
fun ProfileEditScreenPreviewRu() {
    AppTheme {
        ProfileEditScreen(
            editState = ProfileEditState(
                fullName = "Иванов Иван Иванович",
                birthDate = "15.03.1995",
                city = "Москва",
                licenseCategory = "B, B1",
                licenseNumber = "99 12 345678",
                licenseValidUntil = "20.06.2032",
                isValid = true
            ),
            isSaving = false,
            onFieldChange = { _, _ -> },
            onSave = {}
        )
    }
}

@Preview(
    showBackground = true,
    name = "Редактирование EN",
    widthDp = 360,
    heightDp = 750,
    locale = "en"
)
@Composable
fun ProfileEditScreenPreviewEn() {
    AppTheme {
        ProfileEditScreen(
            editState = ProfileEditState(
                fullName = "John Doe",
                birthDate = "15.03.1995",
                city = "New York",
                licenseCategory = "B, B1",
                licenseNumber = "99 12 345678",
                licenseValidUntil = "20.06.2032",
                isValid = true
            ),
            isSaving = false,
            onFieldChange = { _, _ -> },
            onSave = {}
        )
    }
}