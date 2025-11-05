package com.oliviermarteaux.shared.composables

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconToggleButtonColors
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * A customizable rating bar composed of toggleable icons, allowing users to select
 * a rating value visually using a row of icons (e.g., stars).
 *
 * This composable leverages [SharedIconToggle] for each star, providing flexibility
 * to use either vector or painter icons via [IconSource]. It supports full customization
 * for layout, icon appearance, interactivity, and color theming.
 *
 * ### Behavior:
 * - Displays a horizontal row of toggleable icons representing the rating.
 * - Users can select a rating by tapping individual icons.
 * - Supports updating the current rating via [onRatingChanged].
 * - Each icon can be fully customized with [iconChecked], [iconUnchecked], [tint],
 *   and [iconModifier].
 * - Layout and alignment can be adjusted via [horizontalArrangement] and [verticalAlignment].
 * - Respects [enabled] state to disable interactions when necessary.
 *
 * ### Parameters:
 * @param iconChecked The [IconSource] used for filled (selected) icons.
 * @param iconUnchecked The [IconSource] used for unfilled (unselected) icons.
 * @param modifier [Modifier] applied to the overall rating bar container.
 * @param contentDescription Accessibility description for each icon.
 * @param tint Tint color applied to the icons. Defaults to [LocalContentColor.current].
 *
 * @param checked Initial checked state of each toggleable icon. Defaults to `false`.
 * @param onCheckedChange Callback invoked when an individual icon is toggled. Receives the new boolean state.
 * @param enabled Controls whether the icons are interactive.
 * @param colors Color configuration for the toggleable icons ([IconToggleButtonColors]).
 * @param interactionSource Optional [MutableInteractionSource] to observe interactions.
 * @param iconToggleModifier Modifier applied to each individual toggleable icon.
 *
 * @param horizontalArrangement Defines spacing and alignment of icons horizontally. Defaults to [Arrangement.Start].
 * @param verticalAlignment Defines vertical alignment of icons within the row. Defaults to [Alignment.CenterVertically].
 *
 * @param stars Total number of icons to display (e.g., 5 for a 5-star rating system). Defaults to 5.
 * @param iconModifier Modifier applied specifically to the inner icons.
 * @param rating Initial rating value (number of selected icons). Defaults to 0.
 * @param onRatingChanged Callback invoked whenever the rating changes. Returns the new rating as an [Int].
 *
 * ### Example Usage:
 * ```kotlin
 * @Composable
 * fun MovieRating() {
 *     var rating by remember { mutableIntStateOf(3) }
 *     SharedRatingBar(
 *         iconChecked = IconSource.VectorIcon(Icons.Default.Star),
 *         iconUnchecked = IconSource.VectorIcon(Icons.Default.StarBorder),
 *         rating = rating,
 *         onRatingChanged = { rating = it },
 *         tint = Color.Yellow
 *     )
 * }
 * ```
 *
 * @see SharedIconToggle
 * @see IconSource
 * @see IconToggleButtonDefaults
 */
@Composable
fun SharedRatingBar(
    //info SharedIcon parameters
    iconChecked: IconSource,
    iconUnchecked: IconSource,
    modifier : Modifier = Modifier,
    contentDescription: String? = null,
    tint: Color = LocalContentColor.current,
    //info  IconToggleButton Parameters
    checked: Boolean  = false,
    onCheckedChange: (Boolean) -> Unit = {},
    enabled: Boolean = true,
    colors: IconToggleButtonColors = IconButtonDefaults.iconToggleButtonColors(),
    interactionSource: MutableInteractionSource? = null,
    iconToggleModifier: Modifier = Modifier,
    //info  Row Parameters
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    //info  RatingBar Parameters
    stars: Int = 5,                             // total stars
    iconModifier: Modifier = Modifier,          // icon modifier,
    rating: Int = 0,                            // current rating
    onRatingChanged: (Int) -> Unit = {}         // on rating change
)/*:Int*/ {
    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth()
    ) {
        val starSize = maxWidth / (stars + 1)  // +1 for some spacing
        var newRating by remember { mutableIntStateOf(rating) }
        Row(
            modifier = modifier,
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = verticalAlignment
        ) {
            for (i in 1..stars) {
                SharedIconToggle(
                    iconChecked = iconChecked,
                    iconUnchecked = iconUnchecked,
                    tint = tint,
                    modifier = iconToggleModifier.size(starSize),
                    enabled = enabled,
                    onCheckedChange = {
                        newRating = if (i != rating) i else i - 1
                        onRatingChanged(newRating)
                    },
                    checked = i <= rating,
                    contentDescription = contentDescription,
                    interactionSource = interactionSource,
                    colors = colors,
                    iconModifier = iconModifier
                )
            }
        }
    }
//    return rating
}