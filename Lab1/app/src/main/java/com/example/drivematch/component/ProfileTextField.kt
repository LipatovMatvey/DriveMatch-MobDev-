package com.example.drivematch.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.drivematch.model.ProfileError
import com.example.drivematch.view.messageRes

@Composable
fun ProfileTextField(
    @StringRes labelRes: Int,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean,
    error: ProfileError? = null,
    @StringRes hintRes: Int? = null,
    readOnly: Boolean = false
) {
    val supporting = when {
        error != null -> stringResource(error.messageRes())
        hintRes != null -> stringResource(hintRes)
        else -> null
    }
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(labelRes)) },
        isError = error != null,
        supportingText = if (supporting != null) {
            { Text(supporting) }
        } else {
            null
        },
        singleLine = true,
        enabled = enabled,
        readOnly = readOnly,
        modifier = Modifier.fillMaxWidth()
    )
}