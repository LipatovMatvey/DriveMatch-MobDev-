package com.example.drivematch.view

import androidx.annotation.StringRes
import com.example.drivematch.R
import com.example.drivematch.model.CarColor
import com.example.drivematch.model.DriveType
import com.example.drivematch.model.FuelType
import com.example.drivematch.model.TransmissionType

@StringRes
fun CarColor.labelRes(): Int = when (this) {
    CarColor.BLUE -> R.string.color_blue
    CarColor.GRAY -> R.string.color_gray
}

@StringRes
fun DriveType.labelRes(): Int = when (this) {
    DriveType.FRONT -> R.string.drive_front
    DriveType.REAR -> R.string.drive_rear
    DriveType.ALL -> R.string.drive_all
}

@StringRes
fun FuelType.labelRes(): Int = when (this) {
    FuelType.PETROL -> R.string.fuel_petrol
    FuelType.DIESEL -> R.string.fuel_diesel
    FuelType.ELECTRO -> R.string.fuel_electro
}

@StringRes
fun TransmissionType.labelRes(): Int = when (this) {
    TransmissionType.MT -> R.string.transmission_mt
    TransmissionType.AT -> R.string.transmission_at
}