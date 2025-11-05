package com.oliviermarteaux.shared.extensions

import java.text.Normalizer

/**
 * Checks if the string is a valid email format.
 *
 * Uses a regex pattern to validate basic email structure.
 *
 * @receiver The string to validate.
 * @return True if the string matches a valid email pattern, false otherwise.
 */
fun String.isValidEmail(): Boolean {
    val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    return this.matches(emailRegex)
}

/**
 * Removes accents and diacritical marks from the string, returning a normalized ASCII representation.
 *
 * @receiver The string potentially containing accented characters.
 * @return The string with accents removed.
 */
fun String.stripAccents(): String {
    val normalized = Normalizer.normalize(this, Normalizer.Form.NFD)
    return normalized.replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
}

/**
 * Checks whether this [String] meets a "strong password" criteria based on a minimum length
 * and character composition.
 *
 * The string is considered "hard enough" if:
 * 1. Its length is at least [minChar].
 * 2. It contains at least one letter (A-Z or a-z).
 * 3. It contains at least one digit (0-9).
 * 4. It contains at least one special character (non-letter and non-digit).
 *
 * ### Parameters:
 * @param minChar The minimum number of characters required.
 *
 * ### Returns:
 * `true` if the string satisfies all the criteria; `false` otherwise.
 *
 * ### Example Usage:
 * ```kotlin
 * "Abc123!".isHardEnough(6)   // returns true
 * "abc12".isHardEnough(6)     // returns false (length too short)
 * "abcdef!".isHardEnough(6)   // returns false (missing digit)
 * ```
 */
fun String.isHardEnough(minChar: Int): Boolean {
    if (length < minChar) return false

    val hasLetter = any { it.isLetter() }
    val hasDigit = any { it.isDigit() }
    val hasSpecial = any { !it.isLetterOrDigit() }

    return hasLetter && hasDigit && hasSpecial
}