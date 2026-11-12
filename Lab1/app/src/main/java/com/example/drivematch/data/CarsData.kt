package com.example.drivematch.data

import androidx.annotation.DrawableRes
import com.example.drivematch.R
import com.example.drivematch.model.Car
import com.example.drivematch.model.CarColor
import com.example.drivematch.model.DriveType
import com.example.drivematch.model.FuelType
import com.example.drivematch.model.TransmissionType

private fun drawable(@DrawableRes resId: Int): String =
    "android.resource://com.example.drivematch/drawable/$resId"

val carsData = listOf(
    Car(
        id = 1,
        brand = "Skoda",
        model = "Octavia",
        pricePerHour = 350,
        photos = listOf(
            drawable(R.drawable.skoda_octavia_1),
            drawable(R.drawable.skoda_octavia_4),
            drawable(R.drawable.skoda_octavia_5),
            drawable(R.drawable.skoda_octavia_2),
            drawable(R.drawable.skoda_octavia_6),
            drawable(R.drawable.skoda_octavia_7),
            drawable(R.drawable.skoda_octavia_3),
            drawable(R.drawable.skoda_octavia_8),
            drawable(R.drawable.skoda_octavia_10),
            drawable(R.drawable.skoda_octavia_11),
            drawable(R.drawable.skoda_octavia_12),
            drawable(R.drawable.skoda_octavia_13),
            drawable(R.drawable.skoda_octavia_14)
        ),
        horsepower = 150,
        engineVolume = 1.4,
        driveType = DriveType.FRONT,
        fuelType = FuelType.PETROL,
        transmissionType = TransmissionType.AT,
        color = CarColor.BLUE,
        year = 2021
    ),
    Car(
        id = 2,
        brand = "BMW",
        model = "X5",
        pricePerHour = 1000,
        photos = listOf(
            drawable(R.drawable.bmw_x5_1),
            drawable(R.drawable.bmw_x5_2),
            drawable(R.drawable.bmw_x5_3),
            drawable(R.drawable.bmw_x5_4),
            drawable(R.drawable.bmw_x5_5),
            drawable(R.drawable.bmw_x5_6),
            drawable(R.drawable.bmw_x5_7),
            drawable(R.drawable.bmw_x5_8),
            drawable(R.drawable.bmw_x5_9),
            drawable(R.drawable.bmw_x5_10),
            drawable(R.drawable.bmw_x5_12),
            drawable(R.drawable.bmw_x5_12),
        ),
        horsepower = 313,
        engineVolume = 3.0,
        driveType = DriveType.ALL,
        fuelType = FuelType.PETROL,
        transmissionType = TransmissionType.AT,
        color = CarColor.GRAY,
        year = 2023
    ),
    Car(
        id = 3,
        brand = "BMW",
        model = "3 Series",
        pricePerHour = 850,
        photos = listOf(
            drawable(R.drawable.bmw_3_series_1),
            drawable(R.drawable.bmw_3_series_2),
            drawable(R.drawable.bmw_3_series_3),
            drawable(R.drawable.bmw_3_series_4),
            drawable(R.drawable.bmw_3_series_5),
            drawable(R.drawable.bmw_3_series_6),
            drawable(R.drawable.bmw_3_series_7),
            drawable(R.drawable.bmw_3_series_8),
            drawable(R.drawable.bmw_3_series_9),
            drawable(R.drawable.bmw_3_series_10),
        ),
        horsepower = 190,
        engineVolume = 2.0,
        driveType = DriveType.REAR,
        fuelType = FuelType.DIESEL,
        transmissionType = TransmissionType.MT,
        color = CarColor.BLUE,
        year = 2020
    ),
    Car(
        id = 4,
        brand = "Tesla",
        model = "Roadster",
        pricePerHour = 2500,
        photos = listOf(
            drawable(R.drawable.tesla_roadster_1),
            drawable(R.drawable.tesla_roadster_2),
            drawable(R.drawable.tesla_roadster_3),
            drawable(R.drawable.tesla_roadster_4),
            drawable(R.drawable.tesla_roadster_5),
            drawable(R.drawable.tesla_roadster_6),
            drawable(R.drawable.tesla_roadster_7),
            drawable(R.drawable.tesla_roadster_8),
            drawable(R.drawable.tesla_roadster_9),
            drawable(R.drawable.tesla_roadster_10),
        ),
        horsepower = 1200,
        engineVolume = 0.0,
        driveType = DriveType.ALL,
        fuelType = FuelType.ELECTRO,
        transmissionType = TransmissionType.AT,
        color = CarColor.GRAY,
        year = 2028
    )
)
val sampleCar1 = carsData.first()
val sampleCar2 = carsData[1]