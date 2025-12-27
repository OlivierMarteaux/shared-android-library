package com.oliviermarteaux.shared.composables

import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.oliviermarteaux.shared.compose.R
import com.oliviermarteaux.shared.ui.theme.SharedPadding
import java.util.Calendar

@Composable
fun SharedTimeTextField(
    time: String,
    modifier: Modifier = Modifier,
    onTimeChange: (String) -> Unit,
    textFieldModifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // Time Picker
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)
    val timePickerDialog = TimePickerDialog(
        context,
        { _, selectedHour, selectedMinute ->
            onTimeChange("$selectedHour:$selectedMinute")
        }, hour, minute, true
    )

    SharedFilledTextField(
        value = time,
        onValueChange = { },
        label = stringResource(R.string.time),
        placeholder = stringResource(R.string.hh_mm),
        modifier = modifier,
        textFieldModifier = textFieldModifier
            .fillMaxWidth()
            .clickable { timePickerDialog.show() },
        isError = time.isEmpty(),
        errorText = stringResource(R.string.please_enter_a_time),
        enabled = false,
        bottomPadding = SharedPadding.large,
        colors = TextFieldDefaults.colors(
            disabledTextColor = if (time.isEmpty()) {
                MaterialTheme.colorScheme.error
            } else LocalContentColor.current.copy(alpha = 1f),
            disabledLabelColor = if (time.isEmpty()) {
                MaterialTheme.colorScheme.error
            } else LocalContentColor.current.copy(alpha = 1f),
            disabledPlaceholderColor = if (time.isEmpty()) {
                MaterialTheme.colorScheme.error
            } else LocalContentColor.current.copy(alpha = 1f),
            disabledIndicatorColor = Color.Transparent,
        )
    )
}