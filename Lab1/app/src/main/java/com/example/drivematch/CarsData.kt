package com.example.drivematch

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
)
val sampleCar1 = carsData.first()
val sampleCar2 = carsData[1]