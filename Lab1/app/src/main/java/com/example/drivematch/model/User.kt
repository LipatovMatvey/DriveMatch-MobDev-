package com.example.drivematch.model

data class User(
    /**
     * Идентификатор пользователя
     */
    val id: Int,

    /**
     * Полное имя пользователя
     */
    val fullName: String,

    /**
     * Дата рождения пользователя
     */
    val birthDate: String,

    /**
     * Город проживания пользователя
     */
    val city: String,

    /**
     * Категории прав
     */
    val licenseCategory: String,

    /**
     * Номер ВУ
     */
    val licenseNumber: String,

    /**
     * Срок действия ВУ
     */
    val licenseValidUntil: String
)

fun User.hasBlankFields(): Boolean = listOf(
    fullName, birthDate, city, licenseCategory, licenseNumber, licenseValidUntil
).any { it.isBlank() }