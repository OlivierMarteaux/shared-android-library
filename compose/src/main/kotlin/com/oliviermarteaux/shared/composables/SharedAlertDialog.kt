package com.oliviermarteaux.shared.composables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.DialogProperties

/**
 * Displays a customizable alert dialog with a title, message, and confirm/dismiss buttons.
 *
 * This composable wraps [AlertDialog] to provide a reusable dialog component that supports
 * custom text, icons, colors, and shapes. It can be used across the app for consistent
 * alert and confirmation dialogs.
 *
 * ### Behavior:
 * - Shows a dialog with a title, body text, and one or two action buttons.
 * - The **Confirm** button triggers [onConfirm].
 * - The **Dismiss** button triggers [onDismiss].
 * - If [dismissText] is empty, the dismiss button will still be shown but without a label.
 * - Optionally displays an [icon] at the top of the dialog.
 *
 * ### Parameters:
 * @param title The title text displayed at the top of the dialog.
 * @param text The main content or message of the dialog.
 * @param onConfirm Called when the user clicks the confirm button.
 * @param confirmText The text label for the confirm button.
 * @param modifier The [Modifier] applied to the dialog layout.
 * @param onDismiss Called when the user dismisses the dialog or clicks the dismiss button.
 * Defaults to an empty lambda.
 * @param dismissText The text label for the dismiss button.
 * Defaults to an empty string.
 * @param icon Optional composable displayed above the title (e.g., an icon representing the dialog type).
 * @param shape The shape of the dialog container. Defaults to [AlertDialogDefaults.shape].
 * @param containerColor The background color of the dialog container. Defaults to [AlertDialogDefaults.containerColor].
 * @param iconContentColor The color used for the [icon] content. Defaults to [AlertDialogDefaults.iconContentColor].
 * @param titleContentColor The color of the title text. Defaults to [AlertDialogDefaults.titleContentColor].
 * @param textContentColor The color of the dialog text. Defaults to [AlertDialogDefaults.textContentColor].
 * @param tonalElevation The tonal elevation of the dialog surface. Defaults to [AlertDialogDefaults.TonalElevation].
 * @param properties Additional [DialogProperties] for configuring dialog behavior.
 *
 * ### Example Usage:
 * ```kotlin
 * @Composable
 * fun DeleteConfirmationDialog(
 *     onConfirmDelete: () -> Unit,
 *     onDismiss: () -> Unit
 * ) {
 *     SharedAlertDialog(
 *         title = "Delete Item",
 *         text = "Are you sure you want to delete this item? This action cannot be undone.",
 *         confirmText = "Delete",
 *         dismissText = "Cancel",
 *         onConfirm = onConfirmDelete,
 *         onDismiss = onDismiss,
 *         icon = { Icon(Icons.Default.Delete, contentDescription = null) }
 *     )
 * }
 * ```
 *
 * @see AlertDialog
 * @see DialogProperties
 * @see AlertDialogDefaults
 */
@Composable
fun SharedAlertDialog(
    title: String,
    text: String,
    onConfirm: () -> Unit,
    confirmText: String,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    dismissText: String = "",
    icon: @Composable (() -> Unit)? = null,
    shape: Shape = AlertDialogDefaults.shape,
    containerColor: Color = AlertDialogDefaults.containerColor,
    iconContentColor: Color = AlertDialogDefaults.iconContentColor,
    titleContentColor: Color = AlertDialogDefaults.titleContentColor,
    textContentColor: Color = AlertDialogDefaults.textContentColor,
    tonalElevation: Dp = AlertDialogDefaults.TonalElevation,
    properties: DialogProperties = DialogProperties(),
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title) },
        titleContentColor = titleContentColor,
        text = { Text(text) },
        textContentColor = textContentColor,
        confirmButton = { TextButton(onClick = onConfirm) {Text(confirmText)} },
        containerColor = containerColor,
        dismissButton = { TextButton(onClick = onDismiss) {Text(dismissText)} },
        modifier = modifier,
        icon = icon,
        iconContentColor = iconContentColor,
        shape = shape,
        tonalElevation = tonalElevation,
        properties = properties
    )
}