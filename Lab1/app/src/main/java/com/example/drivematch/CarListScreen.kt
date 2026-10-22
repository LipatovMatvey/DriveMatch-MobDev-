package com.example.drivematch

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.drivematch.model.Car
import com.example.drivematch.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarListScreen(
    cars: List<Car>,
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
                }
            )
        }
    ){ innerPadding ->
        LazyColumn(
            modifier = modifier.fillMaxSize().padding(innerPadding),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(cars, key = { it.id }) { car ->
                CarRow(car = car, onCarClick = { onCarClick(car) } )
            }
        }
    }
}

@Composable
fun CarRow(
    car: Car,
    onCarClick: () -> Unit
) {
    Card(
        onClick = onCarClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(
                    R.string.car_title_with_year, car.brand, car.model, car.year
                ),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Image(
                painter = painterResource(car.photos.first()),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 100.dp, height = 70.dp)
                    .clip(MaterialTheme.shapes.medium)
            )
        }
    }
}

@Preview(showBackground = true, name = "Вкладка Автомобили RU", widthDp = 360, heightDp = 640, locale = "ru")
@Composable
fun CarListScreenPreview() {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            CarListScreen(cars = carsData, onCarClick = {} )
        }
    }
}

