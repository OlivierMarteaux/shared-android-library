package com.oliviermarteaux.shared.composables

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.oliviermarteaux.shared.composables.texts.TextTitleLarge
import com.oliviermarteaux.shared.composables.texts.TextTitleSmall

/**
 * A reusable scaffold composable with a top app bar, optional floating action button (FAB),
 * and optional menu actions. This composable wraps [Scaffold] from Material 3 and provides
 * a consistent structure for screens across the app.
 *
 * ### Features:
 * - Top app bar with customizable title, optional back button, and menu actions.
 * - Optional FAB with configurable appearance, behavior, and content description.
 * - Supports up to two menu items in a dropdown menu.
 * - Provides content area with [contentPadding] automatically applied.
 *
 * ### Behavior:
 * - If [onBackClick] is provided, a back navigation icon is displayed in the top app bar.
 * - If [onMenuItem1Click] is provided, a menu icon is displayed; optionally supports a second menu item.
 * - If [onFabClick] is provided, a floating action button is displayed with the standard "Add" icon.
 * - Menu and FAB are disabled if their respective callbacks are `null` or [fabEnabled] is `false`.
 *
 * ### Parameters:
 * @param modifier [Modifier] applied to the scaffold.
 * @param title The text displayed as the top app bar title.
 * @param topAppBarModifier [Modifier] applied to the top app bar.
 * @param onFabClick Optional lambda invoked when the FAB is clicked.
 * @param onBackClick Optional lambda invoked when the back button is clicked.
 * @param onMenuItem1Click Optional lambda for the first menu item click.
 * @param onMenuItem2Click Optional lambda for the second menu item click.
 * @param menuItem1Title Title text for the first menu item.
 * @param menuItem2Title Title text for the second menu item.
 * @param fabEnabled Controls whether the FAB is clickable. Defaults to `true`.
 * @param fabShape Shape of the FAB. Defaults to [FloatingActionButtonDefaults.shape].
 * @param fabContainerColor Background color of the FAB. Defaults to [FloatingActionButtonDefaults.containerColor].
 * @param fabContentColor Content color of the FAB. Defaults to [contentColorFor(fabContainerColor)].
 * @param fabInteractionSource Optional [MutableInteractionSource] for the FAB.
 * @param fabContentDescription Accessibility content description for the FAB.
 * @param content Composable lambda representing the main content of the scaffold. Receives [contentPadding] to account for system bars and FAB.
 *
 * ### Example Usage:
 * ```kotlin
 * @Composable
 * fun ExampleScreen() {
 *     SharedScaffold(
 *         title = "Dashboard",
 *         onBackClick = { /* navigate back */ },
 *         onFabClick = { /* add item */ },
 *         onMenuItem1Click = { /* menu action */ },
 *         menuItem1Title = "Settings",
 *     ) { padding ->
 *         Column(modifier = Modifier.padding(padding)) {
 *             Text("Hello World")
 *         }
 *     }
 * }
 * ```
 *
 * @see Scaffold
 * @see TopAppBar
 * @see FloatingActionButton
 * @see DropdownMenu
 * @see SharedIconButton
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedScaffold(
    modifier: Modifier = Modifier,
    title: String = "",
    topAppBarModifier: Modifier = Modifier,
    onFabClick: (() -> Unit)? = null,
    onBackClick: (() -> Unit)? = null,
    onMenuItem1Click: (() -> Unit)? = null,
    onMenuItem2Click: (() -> Unit)? = null,
    menuItem1Title: String = "",
    menuItem2Title: String = "",
    fabEnabled: Boolean = true,
    fabShape: Shape =  FloatingActionButtonDefaults.shape,
    fabContainerColor: Color =  FloatingActionButtonDefaults.containerColor,
    fabContentColor: Color = contentColorFor(fabContainerColor),
    fabInteractionSource: MutableInteractionSource? = null,
    fabContentDescription: String = "",
    content: @Composable (contentPadding: PaddingValues) -> Unit = {},
){
    var showMenu by rememberSaveable { mutableStateOf(false) }
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { TextTitleLarge(title) },
                modifier = topAppBarModifier,
                navigationIcon = {
                    onBackClick?.let {
                        SharedIconButton(
                            icon = IconSource.VectorIcon(Icons.AutoMirrored.Filled.ArrowBack),
                        ) { onBackClick() }
                    }
                },
                actions = {
                    onMenuItem1Click?.let{
                        SharedIconButton(
                            icon = IconSource.VectorIcon(Icons.Default.MoreVert),
                        ) { showMenu = !showMenu }
                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { TextTitleSmall(text = menuItem1Title) },
                                onClick = {
                                    onMenuItem1Click()
                                    showMenu = false
                                },
                            )
                            onMenuItem2Click?.let {
                                DropdownMenuItem(
                                    text = { TextTitleSmall(text = menuItem2Title) },
                                    onClick = {
                                        onMenuItem2Click()
                                        showMenu = false
                                    },
                                )
                            }
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            onFabClick?.let {
                FloatingActionButton(
                    onClick = { if (fabEnabled) onFabClick() },
                    modifier = modifier
                        .padding(bottom = 20.dp, end = 20.dp)
                        .semantics { contentDescription = fabContentDescription },
                    shape = fabShape,
                    containerColor = fabContainerColor,
                    contentColor = fabContentColor,
                    elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(10.dp),
                    interactionSource = fabInteractionSource
                ) {
                    SharedIcon(
                        icon = IconSource.VectorIcon(Icons.Filled.Add),
                    )
                }
            }
        },
    ) { contentPadding -> content(contentPadding) }
}