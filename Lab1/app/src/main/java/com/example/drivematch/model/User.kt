package com.example.drivematch.model

data class User(
    /**
     *
     */
    val id: Int,

    /**
     *
     */
    val fullName: String,

    /**
     *
     */
    val birthDate: String,

    /**
     *
     */
    val city: String,

    /**
     *
     */
    val licenseCategory: String,

    /**
     *
     */
    val licenseNumber: String,

    /**
     *
     */
    val licenseValidUntil: String
)