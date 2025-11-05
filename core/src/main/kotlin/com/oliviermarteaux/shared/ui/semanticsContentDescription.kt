package com.oliviermarteaux.shared.ui

/**
 * Generates a comprehensive content description string for accessibility (TalkBack)
 * based on multiple parameters such as base description, state, click actions, and additional text.
 *
 * This function is useful for providing semantic descriptions to assistive technologies,
 * combining general content, toggle states, and click/long-click labels into a single string.
 *
 * ### Parameters:
 * @param contentDescription Optional base description of the element.
 * @param state Optional boolean state of the element (e.g., checked/unchecked).
 * @param trueStateDescription Description to use when [state] is `true`.
 * @param falseStateDescription Description to use when [state] is `false`.
 * @param onClickLabel Optional label describing the primary click action.
 * @param onLongClickLabel Optional label describing the long-click action.
 * @param text Optional additional text to include when [state] is `true`.
 *
 * ### Returns:
 * A [String] combining all provided information, formatted for accessibility tools like TalkBack.
 *
 * ### Example Usage:
 * ```kotlin
 * val description = semanticsContentDescription(
 *     contentDescription = "Play/Pause button",
 *     state = isPlaying,
 *     trueStateDescription = "Playing",
 *     falseStateDescription = "Paused",
 *     onClickLabel = "Toggle play state"
 * )
 * // description: "Play/Pause button. Playing. Toggle play state"
 * ```
 */
fun semanticsContentDescription(
    contentDescription: String? = null,
    state: Boolean? = null,
    trueStateDescription: String = "",
    falseStateDescription: String = "",
    onClickLabel: String? = null,
    onLongClickLabel: String? = null,
    text: String = "",
): String {

    val contentDescription: String = contentDescription?.let{"$it. "}?:""
    val stateDescription: String = state?.let{
        if (it) "$trueStateDescription. $text. " else "$falseStateDescription. "
    }?:""
    val clickLabel: String = onClickLabel ?:""
    val longClickLabel = onLongClickLabel ?:""

    val talkback: String = "$contentDescription $stateDescription $clickLabel $longClickLabel"

    return talkback
}