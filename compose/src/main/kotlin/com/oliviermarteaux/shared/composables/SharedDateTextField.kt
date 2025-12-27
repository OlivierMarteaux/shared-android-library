package com.oliviermarteaux.shared.composables

import android.app.DatePickerDialog
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
fun SharedDateTextField(
    date: String,
    modifier: Modifier = Modifier,
    textFieldModifier: Modifier = Modifier,
    onDateChange: (String) -> Unit,
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // Date Picker
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDayOfMonth ->
            onDateChange("$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear")
        }, year, month, day
    )
    SharedFilledTextField(
        value = date,
        onValueChange = { },
        label = stringResource(R.string.date),
        placeholder = stringResource(R.string.mm_dd_yyyy),
        modifier = modifier,
        textFieldModifier = textFieldModifier
            .fillMaxWidth()
            .clickable { datePickerDialog.show() },
        isError = date.isEmpty(),
        errorText = stringResource(R.string.please_enter_a_date),
        enabled = false,
        bottomPadding = SharedPadding.large,
        colors = TextFieldDefaults.colors(
            disabledTextColor = if (date.isEmpty()) {
                MaterialTheme.colorScheme.error
            } else LocalContentColor.current.copy(alpha = 1f),
            disabledLabelColor = if (date.isEmpty()) {
                MaterialTheme.colorScheme.error
            } else LocalContentColor.current.copy(alpha = 1f),
            disabledPlaceholderColor = if (date.isEmpty()) {
                MaterialTheme.colorScheme.error
            } else LocalContentColor.current.copy(alpha = 1f),
            disabledIndicatorColor = Color.Transparent,
        )
    )
}