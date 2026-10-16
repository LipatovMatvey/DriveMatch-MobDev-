package com.example.drivematch

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.drivematch.snackbar
import com.example.drivematch.ui.theme.AppTheme


@Composable
fun CarDetailsScreen(
    car: Car,
    modifier: Modifier = Modifier
) {
    val notifier = MaterialTheme.snackbar

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        CarPhotoCarousel(photos = car.photos)

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${car.brand} ${car.model}",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.weight(1f)
            )
            OutlinedButton(onClick = notifier::notifyPlaceholder) {
                Text(
                    text = "❤\uFE0F",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.price_per_hour, car.pricePerHour),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.weight(1f)
            )
            Button(onClick = notifier::notifyPlaceholder) {
                Text(
                    text = stringResource(R.string.rent_car),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedCard(modifier = Modifier.fillMaxWidth()) {
            SpecRow(R.string.spec_power, stringResource(R.string.value_hp, car.horsepower))
            HorizontalDivider()
            SpecRow(R.string.spec_engine_volume, stringResource(R.string.value_volume, car.engineVolume))
            HorizontalDivider()
            SpecRow(R.string.spec_transmission_type, stringResource(car.transmissionType.labelRes, car.transmissionType))
            HorizontalDivider()
            SpecRow(R.string.spec_drive, stringResource(car.driveType.labelRes))
            HorizontalDivider()
            SpecRow(R.string.spec_fuel, stringResource(car.fuelType.labelRes))
            HorizontalDivider()
            SpecRow(R.string.spec_color, stringResource(car.colorRes))
            HorizontalDivider()
            SpecRow(R.string.spec_year, car.year.toString())
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarPhotoCarousel(
    photos: List<Int>,
    modifier: Modifier = Modifier
) {
    val carouselState = rememberCarouselState { photos.size }

    HorizontalMultiBrowseCarousel(
        state = carouselState,
        preferredItemWidth = 260.dp,
        itemSpacing = 8.dp,
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
    ) { index ->
        Image(
            painter = painterResource(photos[index]),
            contentDescription = stringResource(
                R.string.car_photo_description, index + 1, photos.size
            ),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(200.dp)
                .maskClip(MaterialTheme.shapes.extraLarge)
        )
    }
}

@Composable
private fun SpecRow(@StringRes labelRes: Int, value: String) {
    ListItem(
        headlineContent = { Text(stringResource(labelRes)) },
        trailingContent = {
            Text(text = value, style = MaterialTheme.typography.titleMedium)
        }
    )
}

@Preview(showBackground = true, name = "Автомобиль RU", widthDp = 360, heightDp = 640, locale = "ru")
@Composable
fun CarDetailsScreenPreviewRu() {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            CarDetailsScreen(car = sampleCar1)
        }
    }
}

@Preview(showBackground = true, name = "Автомобиль EN", widthDp = 360, heightDp = 640, locale = "en")
@Composable
fun CarDetailsScreenPreviewEn() {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            CarDetailsScreen(car = sampleCar2)
        }
    }
}