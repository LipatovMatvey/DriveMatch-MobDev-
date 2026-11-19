package com.example.drivematch.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.format.ResolverStyle

enum class ProfileField { FULL_NAME, CITY, LICENSE_CATEGORY, LICENSE_NUMBER, LICENSE_VALID_UNTIL }

enum class ProfileError { EMPTY, INVALID_FULL_NAME, INVALID_LICENSE_NUMBER, INVALID_DATE, LICENSE_EXPIRED }

private val WHITESPACE = Regex("\\s+")

private val NAME_WORD = Regex("\\p{L}+(?:[-'’]\\p{L}+)*")

private val LICENSE_NUMBER = Regex("\\d{10}")

private val DATE_FORMAT: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.uuuu").withResolverStyle(ResolverStyle.STRICT)

private const val MIN_NAME_WORDS = 2
private const val MAX_NAME_WORDS = 3
private const val MAX_NAME_LENGTH = 100

fun User.validate(today: LocalDate = LocalDate.now()): Map<ProfileField, ProfileError> {
    val errors = mutableMapOf<ProfileField, ProfileError>()
    validateFullName(fullName)?.let { errors[ProfileField.FULL_NAME] = it }
    if (city.isBlank()) errors[ProfileField.CITY] = ProfileError.EMPTY
    if (licenseCategory.isBlank()) errors[ProfileField.LICENSE_CATEGORY] = ProfileError.EMPTY
    validateLicenseNumber(licenseNumber)?.let { errors[ProfileField.LICENSE_NUMBER] = it }
    validateValidUntil(licenseValidUntil, today)?.let {
        errors[ProfileField.LICENSE_VALID_UNTIL] = it
    }
    return errors
}

fun User.normalized(): User = copy(
    fullName = fullName.trim().split(WHITESPACE).joinToString(" "),
    city = city.trim(),
    licenseCategory = licenseCategory.trim(),
    licenseNumber = formatLicenseNumber(licenseNumber),
    licenseValidUntil = licenseValidUntil.trim()
)

private fun validateFullName(text: String): ProfileError? {
    val value = text.trim()
    if (value.isEmpty()) return ProfileError.EMPTY
    val words = value.split(WHITESPACE)
    val valid = value.length <= MAX_NAME_LENGTH &&
            words.size in MIN_NAME_WORDS..MAX_NAME_WORDS &&
            words.all { NAME_WORD.matches(it) }
    return if (valid) null else ProfileError.INVALID_FULL_NAME
}

private fun licenseDigits(text: String): String = text.filterNot { it.isWhitespace() }

private fun validateLicenseNumber(text: String): ProfileError? = when {
    text.isBlank() -> ProfileError.EMPTY
    LICENSE_NUMBER.matches(licenseDigits(text)) -> null
    else -> ProfileError.INVALID_LICENSE_NUMBER
}

private fun formatLicenseNumber(text: String): String {
    val digits = licenseDigits(text)
    return if (LICENSE_NUMBER.matches(digits)) {
        "${digits.take(2)} ${digits.substring(2, 4)} ${digits.drop(4)}"
    } else {
        text.trim()
    }
}

private fun parseDate(text: String): LocalDate? =
    try {
        LocalDate.parse(text.trim(), DATE_FORMAT)
    } catch (e: DateTimeParseException) {
        null
    }

private fun validateValidUntil(text: String, today: LocalDate): ProfileError? {
    if (text.isBlank()) return ProfileError.EMPTY
    val date = parseDate(text) ?: return ProfileError.INVALID_DATE
    return if (date.isBefore(today)) ProfileError.LICENSE_EXPIRED else null
}