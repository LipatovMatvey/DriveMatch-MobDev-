package com.example.drivematch.view


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.drivematch.R
import com.example.drivematch.component.SpecRow
import com.example.drivematch.data.sampleUser1
import com.example.drivematch.model.RentalItem
import com.example.drivematch.model.User
import com.example.drivematch.ui.theme.AppTheme
import androidx.compose.material3.ListItem
import com.example.drivematch.data.sampleCar1
import com.example.drivematch.model.Rental

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    profile: User,
    rentals: List<RentalItem>,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.profile_label),
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(120.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = stringResource(R.string.profile_photo_description),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(28.dp)
                )
            }
            Spacer(Modifier.height(16.dp))
            Text(
                text = profile.fullName,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(24.dp))
            OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                SpecRow(R.string.profile_birth_date, profile.birthDate)
                HorizontalDivider()
                SpecRow(R.string.profile_city, profile.city)
                HorizontalDivider()
                SpecRow(R.string.profile_license_category, profile.licenseCategory)
                HorizontalDivider()
                SpecRow(R.string.profile_license_number, profile.licenseNumber)
                HorizontalDivider()
                SpecRow(R.string.profile_license_valid_until, profile.licenseValidUntil)
            }
            Spacer(Modifier.height(24.dp))
            Text(
                text = stringResource(R.string.profile_rentals_title),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(Modifier.height(8.dp))
            if (rentals.isEmpty()) {
                Text(
                    text = stringResource(R.string.profile_rentals_empty),
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                    rentals.forEachIndexed { index, item ->
                        if (index > 0) {
                            HorizontalDivider()
                        }
                        ListItem(
                            headlineContent = { Text("${item.car.brand} ${item.car.model}") },
                            supportingContent = {
                                Text(stringResource(R.string.rental_hours_value, item.rental.hours))
                            },
                            trailingContent = {
                                Text(
                                    text = stringResource(
                                        R.string.rental_price_value,
                                        item.rental.totalPrice
                                    ),
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        )
                    }
                }
            }
            Spacer(Modifier.height(24.dp))
            Button(
                onClick = onEditClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Filled.Edit, contentDescription = null)
                Spacer(Modifier.size(8.dp))
                Text(
                    text = stringResource(R.string.profile_edit),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Профиль RU", widthDp = 360, heightDp = 750, locale = "ru")
@Composable
fun ProfileScreenPreviewRu() {
    AppTheme {
        ProfileScreen(
            profile = sampleUser1,
            rentals = listOf(RentalItem(Rental(1, 1, 3, 1050), sampleCar1)),
            onEditClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Профиль EN", widthDp = 360, heightDp = 750, locale = "en")
@Composable
fun ProfileScreenPreviewEn() {
    AppTheme {
        ProfileScreen(
            profile = sampleUser1,
            rentals = emptyList(),
            onEditClick = {}
        )
    }
}