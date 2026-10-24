package com.example.drivematch.view

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.drivematch.R
import com.example.drivematch.data.sampleCar2
import com.example.drivematch.ui.theme.AppTheme

@Composable
fun CarsharingAboutScreen(
    modifier: Modifier = Modifier,
    nameOrganizations: String,
    carsCount: Int
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = nameOrganizations,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.home_tagline),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(24.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            StatCard(
                value = stringResource(R.string.home_stat_years_value),
                label = stringResource(R.string.home_stat_years_label),
                modifier = Modifier.weight(1f)
            )
            StatCard(
                value = carsCount.toString(),
                label = stringResource(R.string.home_stat_cars_label),
                modifier = Modifier.weight(1f)
            )
            StatCard(
                value = stringResource(R.string.home_stat_support_value),
                label = stringResource(R.string.home_stat_support_label),

                modifier = Modifier.weight(1f)
            )
        }
        Spacer(Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.home_about),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.home_why_title),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.Start)
        )
        listOf(
            R.string.home_adv_1,
            R.string.home_adv_2,
            R.string.home_adv_3,
            R.string.home_adv_4
        ).forEach { res ->
            ListItem(
                leadingContent = {
                    Icon(
                        Icons.Filled.Check,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                headlineContent = { Text(stringResource(res)) }
            )
        }
    }
}

@Composable
private fun StatCard(value: String, label: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true, name = "Автомобиль EN", widthDp = 360, heightDp = 750, locale = "en")
@Composable
fun CarsharingAboutScreenPreviewEn() {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            CarsharingAboutScreen(
                nameOrganizations = NAME_ORGANIZATION,
                carsCount = 256
            )
        }
    }
}

@Preview(showBackground = true, name = "Автомобиль EN", widthDp = 360, heightDp = 750, locale = "ru")
@Composable
fun CarsharingAboutScreenPreviewRu() {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            CarsharingAboutScreen(
                nameOrganizations = NAME_ORGANIZATION,
                carsCount = 256
            )
        }
    }
}