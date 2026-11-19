package com.example.drivematch.view

import androidx.annotation.StringRes
import com.example.drivematch.R
import com.example.drivematch.model.ProfileError

@StringRes
fun ProfileError.messageRes(): Int = when (this) {
    ProfileError.EMPTY -> R.string.error_field_empty
    ProfileError.INVALID_FULL_NAME -> R.string.error_full_name
    ProfileError.INVALID_LICENSE_NUMBER -> R.string.error_license_number
    ProfileError.INVALID_DATE -> R.string.error_date_format
    ProfileError.LICENSE_EXPIRED -> R.string.error_license_expired
}