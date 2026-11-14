package com.example.drivematch.data.remote

import com.example.drivematch.model.User
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Int,
    val fullName: String,
    val birthDate: String,
    val city: String,
    val licenseCategory: String,
    val licenseNumber: String,
    val licenseValidUntil: String
)

fun UserDto.toModel(): User = User(
    id = id,
    fullName = fullName,
    birthDate = birthDate,
    city = city,
    licenseCategory = licenseCategory,
    licenseNumber = licenseNumber,
    licenseValidUntil = licenseValidUntil
)

fun User.toDto(): UserDto = UserDto(
    id = id,
    fullName = fullName,
    birthDate = birthDate,
    city = city,
    licenseCategory = licenseCategory,
    licenseNumber = licenseNumber,
    licenseValidUntil = licenseValidUntil
)