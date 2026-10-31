package com.example.drivematch.component

import androidx.annotation.StringRes
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun SpecRow(@StringRes labelRes: Int, value: String) {
    ListItem(
        headlineContent = { Text(stringResource(labelRes)) },
        trailingContent = {
            Text(text = value, style = MaterialTheme.typography.titleMedium)
        }
    )
}