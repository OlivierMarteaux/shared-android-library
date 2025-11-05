package com.oliviermarteaux.shared.utils

/**
 * Represents the state of a switch or toggle as either enabled or disabled.
 *
 * Each enum value contains a [title] describing the state in a human-readable format.
 *
 * ### Enum Values:
 * - [ENABLED]: Represents the switch being active or turned on.
 * - [DISABLED]: Represents the switch being inactive or turned off.
 *
 * ### Example Usage:
 * ```kotlin
 * var state = SwitchState.ENABLED
 * if (state == SwitchState.DISABLED) {
 *     // Perform actions for disabled state
 * }
 * ```
 */
enum class SwitchState(private val title: String) {
    ENABLED("Enabled"),
    DISABLED("Disabled")
}