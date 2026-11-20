package com.example.drivematch.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.drivematch.R
import com.example.drivematch.component.CarRow
import com.example.drivematch.component.CenteredMessage
import com.example.drivematch.data.carsData
import com.example.drivematch.model.Car
import com.example.drivematch.ui.theme.AppTheme
import com.example.drivematch.viewmodel.CarsUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarListScreen(
    uiState: CarsUiState,
    onCarClick: (Car) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.cars_in_stock),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                windowInsets = WindowInsets(0, 0, 0, 0)
            )
        }
    ) { innerPadding ->
        if (uiState.cars.isEmpty()) {
            CenteredMessage(
                text = stringResource(R.string.cars_empty),
                modifier = Modifier.padding(innerPadding)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.cars, key = { it.id }) { car ->
                    CarRow(car = car, onCarClick = { onCarClick(car) })
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    name = "Вкладка Автомобили RU",
    widthDp = 360,
    heightDp = 640,
    locale = "ru"
)
@Composable
fun CarListScreenPreviewRu() {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            CarListScreen(
                uiState = CarsUiState(cars = carsData, isLoading = false),
                onCarClick = {}
            )
        }
    }
}

@Preview(
    showBackground = true,
    name = "Вкладка Автомобили EN",
    widthDp = 360,
    heightDp = 640,
    locale = "en"
)
@Composable
fun CarListScreenPreviewEn() {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            CarListScreen(
                uiState = CarsUiState(cars = carsData, isLoading = false),
                onCarClick = {}
            )
        }
    }
}

@Preview(
    showBackground = true,
    name = "Пустой список",
    widthDp = 360,
    heightDp = 640,
    locale = "en"
)
@Composable
fun CarListScreenEmptyPreviewEn() {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            CarListScreen(
                uiState = CarsUiState(cars = emptyList(), isLoading = false),
                onCarClick = {}
            )
        }
    }
}

@Preview(
    showBackground = true,
    name = "Пустой список",
    widthDp = 360,
    heightDp = 640,
    locale = "ru"
)
@Composable
fun CarListScreenEmptyPreviewRu() {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            CarListScreen(
                uiState = CarsUiState(cars = emptyList(), isLoading = false),
                onCarClick = {}
            )
        }
    }
}

