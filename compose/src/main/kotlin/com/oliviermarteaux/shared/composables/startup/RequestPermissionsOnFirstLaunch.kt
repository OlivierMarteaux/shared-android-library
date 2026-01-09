package com.oliviermarteaux.shared.composables.startup

import android.Manifest
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.edit
import com.oliviermarteaux.shared.compose.R

/**
 * Requests runtime permissions the first time the app is launched.
 *
 * This composable checks a shared preference (`isFirstLaunch`) to determine if it's the user's first time
 * using the app. If so, it triggers a system permission request (e.g., for [Manifest.permission.CALL_PHONE]).
 *
 * If the user denies the permissions, an [AlertDialog] is displayed to explain why the permissions are needed.
 *
 * This pattern helps ensure permissions are only requested once during the initial launch,
 * avoiding repeated interruptions in future sessions.
 *
 * @see ActivityResultContracts.RequestMultiplePermissions
 * @see LocalContext
 * @see rememberLauncherForActivityResult
 */
@Composable
fun RequestPermissionsOnFirstLaunch(
    permission1: String,
    permission2: String? = null,
    permission3: String? = null,
    permission4: String? = null,
    permission5: String? = null
) {
    val context = LocalContext.current

    val permissions = listOfNotNull(
        permission1 ,permission2, permission3, permission4, permission5
    )

    var alertDialog by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        val allGranted = permissionsMap.all { it.value }
        if (!allGranted) { alertDialog = true
        }
    }

    LaunchedEffect(Unit) {
        val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val isFirstLaunch = prefs.getBoolean("isFirstLaunch", true)

        if (isFirstLaunch) {
            prefs.edit { putBoolean("isFirstLaunch", false) }
            launcher.launch(permissions.toTypedArray())
        }
    }

    if (alertDialog){
        AlertDialog(
            onDismissRequest = {},
            title = { Text(stringResource(R.string.calls_denied)) },
            text = { Text(stringResource(R.string.call_feature_message)) },
            confirmButton = { TextButton(onClick = { alertDialog = false }) { Text(text = stringResource(
                R.string.ok), color = MaterialTheme.colorScheme.onPrimary) } },
            containerColor = MaterialTheme.colorScheme.primary,
        )
    }
}