package com.example.drivematch.data.remote

import com.example.drivematch.model.Car
import com.example.drivematch.model.CarColor
import com.example.drivematch.model.DriveType
import com.example.drivematch.model.FuelType
import com.example.drivematch.model.TransmissionType
import kotlinx.serialization.Serializable

@Serializable
data class CarDto(
    val id: Int,
    val brand: String,
    val model: String,
    val pricePerHour: Int,
    val photos: List<String>,
    val horsepower: Int,
    val engineVolume: Double,
    val driveType: DriveType,
    val fuelType: FuelType,
    val transmissionType: TransmissionType,
    val color: CarColor,
    val year: Int
)

fun CarDto.toModel(baseUrl: String): Car = Car(
    id = id,
    brand = brand,
    model = model,
    pricePerHour = pricePerHour,
    photos = photos.map { baseUrl + it },
    horsepower = horsepower,
    engineVolume = engineVolume,
    driveType = driveType,
    fuelType = fuelType,
    transmissionType = transmissionType,
    color = color,
    year = year
)