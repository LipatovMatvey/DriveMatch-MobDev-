package com.example.drivematch

import androidx.annotation.StringRes

enum class DriveType(@StringRes val labelRes: Int) {
    FRONT(R.string.drive_front),
    REAR(R.string.drive_rear),
    ALL(R.string.drive_all)
}

enum class FuelType(@StringRes val labelRes: Int) {
    PETROL(R.string.fuel_petrol),
    DIESEL(R.string.fuel_diesel)
}

data class Car(
    val brand: String,
    val model: String,
    val pricePerHour: Int,
    val photos: List<Int>,
    val horsepower: Int,
    val engineVolume: Double,
    val driveType: DriveType,
    val fuelType: FuelType,
    @StringRes val colorRes: Int,
    val year: Int
)

// временные данные для экрана и превью
val sampleCar = Car(
    brand = "Skoda",
    model = "Octavia",
    pricePerHour = 350,
    photos = listOf(
        R.drawable.skoda_octavia_1,
        R.drawable.skoda_octavia_4,
        R.drawable.skoda_octavia_5,
        R.drawable.skoda_octavia_2,
        R.drawable.skoda_octavia_6,
        R.drawable.skoda_octavia_7,
        R.drawable.skoda_octavia_3,
        R.drawable.skoda_octavia_8,
        R.drawable.skoda_octavia_10,
        R.drawable.skoda_octavia_11,
        R.drawable.skoda_octavia_12,
        R.drawable.skoda_octavia_13,
        R.drawable.skoda_octavia_14
    ),
    horsepower = 150,
    engineVolume = 1.4,
    driveType = DriveType.FRONT,
    fuelType = FuelType.PETROL,
    colorRes = R.string.color_white,
    year = 2021
)