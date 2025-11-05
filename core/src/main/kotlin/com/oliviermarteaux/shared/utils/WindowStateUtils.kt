package com.oliviermarteaux.shared.utils

/**
 * Represents the types of navigation patterns that can be used in the app's UI.
 *
 * ### Enum Values:
 * - [BOTTOM_NAVIGATION]: Standard bottom navigation bar.
 * - [NAVIGATION_RAIL]: Vertical navigation rail, usually for larger screens.
 * - [PERMANENT_NAVIGATION_DRAWER]: Permanent navigation drawer, often on tablets or desktops.
 *
 * ### Example Usage:
 * ```kotlin
 * val navType = SharedNavigationType.BOTTOM_NAVIGATION
 * ```
 */
enum class SharedNavigationType {
    BOTTOM_NAVIGATION, NAVIGATION_RAIL, PERMANENT_NAVIGATION_DRAWER
}


/**
 * Represents the type of content layout in a screen.
 *
 * ### Enum Values:
 * - [LIST_ONLY]: Screen displays only a list of items.
 * - [LIST_AND_DETAIL]: Screen displays both a list and detail view side by side.
 *
 * ### Example Usage:
 * ```kotlin
 * val contentType = SharedContentType.LIST_AND_DETAIL
 * ```
 */
enum class SharedContentType {
    LIST_ONLY, LIST_AND_DETAIL
}