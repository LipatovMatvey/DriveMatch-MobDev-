package com.example.drivematch.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.drivematch.R
import com.example.drivematch.data.sampleCar1
import com.example.drivematch.ui.theme.AppTheme
import com.example.drivematch.viewmodel.RentalsUiState


@Composable
fun RentScreen(
    uiState: RentalsUiState,
    onIncreaseHours: () -> Unit,
    onDecreaseHours: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    val car = uiState.selectedCar
    if (car == null) {
        Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "${car.brand} ${car.model}",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.price_per_hour, car.pricePerHour),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.rent_hours),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedIconButton(
                onClick = onDecreaseHours,
                enabled = uiState.canDecreaseHours && ! uiState.isSubmitting
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.rent_hours_decrease)
                )
            }
            Text(
                text = uiState.hours.toString(),
                style = MaterialTheme.typography.headlineMedium
            )
            OutlinedIconButton(
                onClick = onIncreaseHours,
                enabled = uiState.canIncreaseHours && ! uiState.isSubmitting
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = stringResource(R.string.rent_hours_increase)
                )
            }
        }
        Spacer(Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.rent_total, uiState.totalPrice),
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = onConfirm,
            enabled = ! uiState.isSubmitting,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.rent_confirm),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview(showBackground = true, name = "Аренда RU", widthDp = 360, heightDp = 640, locale = "ru")
@Composable
fun RentScreenPreviewRu() {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            RentScreen(
                uiState = RentalsUiState(selectedCar = sampleCar1, hours = 3),
                onIncreaseHours = {},
                onDecreaseHours = {},
                onConfirm = {})
        }
    }
}

@Preview(showBackground = true, name = "Аренда EN", widthDp = 360, heightDp = 640, locale = "en")
@Composable
fun RentScreenPreviewEn() {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            RentScreen(
                uiState = RentalsUiState(selectedCar = sampleCar1, hours = 3),
                onIncreaseHours = {},
                onDecreaseHours = {},
                onConfirm = {})
        }
    }
}