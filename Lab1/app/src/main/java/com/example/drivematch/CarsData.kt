package com.example.drivematch

import com.example.drivematch.model.Car
import com.example.drivematch.model.DriveType
import com.example.drivematch.model.FuelType
import com.example.drivematch.model.TransmissionType

val carsData = listOf(
    Car(
        id = 1,
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
        transmissionType = TransmissionType.AT,
        colorRes = R.string.color_blue,
        year = 2021
    ),
    Car(
        id = 2,
        brand = "BMW",
        model = "X5",
        pricePerHour = 1000,
        photos = listOf(
            R.drawable.bmw_x5_1,
            R.drawable.bmw_x5_2,
            R.drawable.bmw_x5_3,
            R.drawable.bmw_x5_4,
            R.drawable.bmw_x5_5,
            R.drawable.bmw_x5_6,
            R.drawable.bmw_x5_7,
            R.drawable.bmw_x5_8,
            R.drawable.bmw_x5_9,
            R.drawable.bmw_x5_10,
            R.drawable.bmw_x5_11,
            R.drawable.bmw_x5_12,
        ),
        horsepower = 313,
        engineVolume = 3.0,
        driveType = DriveType.ALL,
        fuelType = FuelType.PETROL,
        transmissionType = TransmissionType.AT,
        colorRes = R.string.color_gray,
        year = 2023
    ),
    Car(
        id = 3,
        brand = "BMW",
        model = "3 Series",
        pricePerHour = 850,
        photos = listOf(
            R.drawable.bmw_3_series_1,
            R.drawable.bmw_3_series_2,
            R.drawable.bmw_3_series_3,
            R.drawable.bmw_3_series_4,
            R.drawable.bmw_3_series_5,
            R.drawable.bmw_3_series_6,
            R.drawable.bmw_3_series_7,
            R.drawable.bmw_3_series_8,
            R.drawable.bmw_3_series_9,
            R.drawable.bmw_3_series_10,
        ),
        horsepower = 190,
        engineVolume = 2.0,
        driveType = DriveType.REAR,
        fuelType = FuelType.DIESEL,
        transmissionType = TransmissionType.MT,
        colorRes = R.string.color_blue,
        year = 2020
    ),
    Car(
        id = 4,
        brand = "Tesla",
        model = "Roadster",
        pricePerHour = 2500,
        photos = listOf(
            R.drawable.tesla_roadster_1,
            R.drawable.tesla_roadster_2,
            R.drawable.tesla_roadster_3,
            R.drawable.tesla_roadster_4,
            R.drawable.tesla_roadster_5,
            R.drawable.tesla_roadster_6,
            R.drawable.tesla_roadster_7,
            R.drawable.tesla_roadster_8,
            R.drawable.tesla_roadster_9,
            R.drawable.tesla_roadster_10,
        ),
        horsepower = 1200,
        engineVolume = 0.0,
        driveType = DriveType.ALL,
        fuelType = FuelType.ELECTRO,
        transmissionType = TransmissionType.AT,
        colorRes = R.string.color_gray,
        year = 2028
    )
)
val sampleCar1 = carsData.first()
val sampleCar2 = carsData[1]