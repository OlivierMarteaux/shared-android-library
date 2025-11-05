package com.oliviermarteaux.shared.test.extensions

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.rules.ActivityScenarioRule

/**
 * Finds a Compose [SemanticsNodeInteraction] using a string resource ID from the activity.
 *
 * This extension function allows you to query a Compose node by its localized string
 * resource instead of hardcoding the text. Optionally, you can pass a [param] if the
 * string resource is formatted (e.g., contains placeholders).
 *
 * ### Type Parameters:
 * @param A The type of the [ComponentActivity] associated with the Compose test rule.
 *
 * ### Parameters:
 * @param id The string resource ID to search for in the Compose hierarchy.
 * @param param Optional string parameter to format the string resource (for resources with placeholders).
 * @param useUnmergedTree Whether to query the unmerged semantics tree. Default is `true`.
 *
 * ### Returns:
 * A [SemanticsNodeInteraction] that represents the Compose node matching the resolved string.
 *
 * ### Example Usage:
 * ```kotlin
 * composeRule.onNodeWithStringId(R.string.submit_button).performClick()
 * composeRule.onNodeWithStringId(R.string.greeting, "John").assertIsDisplayed()
 * ```
 */
fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.onNodeWithStringId(
    @StringRes id: Int, param: String? = null, useUnmergedTree: Boolean = true
): SemanticsNodeInteraction = onNodeWithText(
    text = activity.getString(id, param),
    useUnmergedTree = useUnmergedTree
)