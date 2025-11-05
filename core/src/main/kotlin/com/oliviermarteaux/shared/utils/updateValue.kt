package com.oliviermarteaux.shared.utils

/**
 * Applies a transformation function to a given value and returns the updated result.
 *
 * This function is a simple utility to create an updated version of a value
 * without modifying the original directly.
 *
 * ### Type Parameters:
 * @param T The type of the value being updated.
 *
 * ### Parameters:
 * @param value The original value to be transformed.
 * @param update A lambda function that takes the current value and returns the updated value.
 *
 * ### Returns:
 * The result of applying [update] to [value].
 *
 * ### Example Usage:
 * ```kotlin
 * val number = 5
 * val newNumber = updateValue(number) { it * 2 } // newNumber == 10
 * ```
 */
fun <T> updateValue(value: T, update: (T) -> T): T {
    return update(value)
}