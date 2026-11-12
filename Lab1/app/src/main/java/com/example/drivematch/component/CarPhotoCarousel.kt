package com.example.drivematch.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.drivematch.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarPhotoCarousel(
    photos: List<String>,
    modifier: Modifier = Modifier
) {
    val carouselState = rememberCarouselState { photos.size }
    val placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceVariant)
    HorizontalMultiBrowseCarousel(
        state = carouselState,
        preferredItemWidth = 260.dp,
        itemSpacing = 8.dp,
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
    ) { index ->
        AsyncImage(
            model = photos[index],
            contentDescription = stringResource(
                R.string.car_photo_description,
                index + 1,
                photos.size
            ),
            placeholder = placeholder,
            error = placeholder,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(200.dp)
                .maskClip(MaterialTheme.shapes.extraLarge)
        )
    }
}