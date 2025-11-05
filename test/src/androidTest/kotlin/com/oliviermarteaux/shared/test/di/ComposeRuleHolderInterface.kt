package com.oliviermarteaux.shared.com.oliviermarteaux.shared.test.di

import androidx.compose.ui.test.junit4.AndroidComposeTestRule

/**
 * Provides access to a Jetpack Compose test rule for UI testing.
 *
 * Implementations of this interface are expected to supply a [composeRule] instance,
 * which is an [AndroidComposeTestRule] used to query, interact with, and assert
 * on Compose UI elements during automated tests.
 *
 * ### Properties:
 * @property composeRule The Compose test rule used to perform UI tests.
 *
 * ### Example Usage:
 * ```kotlin
 * class MyComposeTestRuleHolder : ComposeRuleHolderInterface {
 *     override val composeRule = createComposeRule() // AndroidComposeTestRule
 * }
 * ```
 */
interface ComposeRuleHolderInterface {
    val composeRule: AndroidComposeTestRule<*,*>
}