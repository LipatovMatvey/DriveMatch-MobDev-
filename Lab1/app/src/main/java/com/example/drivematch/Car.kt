package com.example.drivematch

import androidx.annotation.StringRes
import com.example.drivematch.R

enum class DriveType(@StringRes val labelRes: Int) {
    FRONT(R.string.drive_front),
    REAR(R.string.drive_rear),
    ALL(R.string.drive_all)
}

enum class FuelType(@StringRes val labelRes: Int) {
    PETROL(R.string.fuel_petrol),
    DIESEL(R.string.fuel_diesel),
    ELECTRO(R.string.fuel_electro),
}

enum class TransmissionType(@StringRes val labelRes: Int) {
    MT(R.string.transmission_mt),
    AT(R.string.transmission_at)
}

data class Car(
    val id: Int,
    val brand: String,
    val model: String,
    val pricePerHour: Int,
    val photos: List<Int>,
    val horsepower: Int,
    val engineVolume: Double,
    val driveType: DriveType,
    val fuelType: FuelType,
    val transmissionType: TransmissionType,
    @StringRes val colorRes: Int,
    val year: Int
)
