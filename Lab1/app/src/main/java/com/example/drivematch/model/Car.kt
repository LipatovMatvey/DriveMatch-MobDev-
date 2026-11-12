package com.example.drivematch.model

enum class DriveType { FRONT, REAR, ALL }

enum class FuelType { PETROL, DIESEL, ELECTRO }

enum class TransmissionType { MT, AT }

enum class CarColor { BLUE, GRAY }

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
    val photos: List<String>,

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
    val color: CarColor,

    /**
     * Год выпуска
     */
    val year: Int
) {
    /**
     * Расчет цены
     */
    fun priceFor(hours: Int): Int = pricePerHour * hours
}
