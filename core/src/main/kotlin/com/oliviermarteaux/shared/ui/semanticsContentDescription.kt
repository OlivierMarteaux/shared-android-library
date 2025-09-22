package com.oliviermarteaux.shared.ui

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