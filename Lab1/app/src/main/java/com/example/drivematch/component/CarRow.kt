package com.example.drivematch.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.drivematch.R
import com.example.drivematch.model.Car

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
            AsyncImage(
                model = car.photos.firstOrNull(),
                contentDescription = null,
                placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
                error = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 100.dp, height = 70.dp)
                    .clip(MaterialTheme.shapes.medium)
            )
        }
    }
}