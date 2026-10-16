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
    /**
     * Идентификатор автомобиля
     */
    val id: Int,

    /**
     * Марка
     */
    val brand: String,

    /**
     * Модель
     */
    val model: String,

    /**
     * Цена аренды
     */
    val pricePerHour: Int,

    /**
     * Фотографии авто
     */
    val photos: List<Int>,

    /**
     * Мощность
     */
    val horsepower: Int,

    /**
     * Объем двигателя
     */
    val engineVolume: Double,

    /**
     * Тип привода
     */
    val driveType: DriveType,

    /**
     * Тип топлива
     */
    val fuelType: FuelType,

    /**
     * Тип КПП
     */
    val transmissionType: TransmissionType,

    /**
     * Цвет
     */
    @StringRes val colorRes: Int,

    /**
     * Год выпуска
     */
    val year: Int
)
