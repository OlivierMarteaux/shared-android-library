package com.oliviermarteaux.shared.composables

import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri

@Composable
fun WebsiteButton(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // ✅ Read theme in composable scope
    val primaryColor = MaterialTheme.colorScheme.primary.toArgb()

    Button(
        onClick = {
            val colorScheme = CustomTabColorSchemeParams.Builder()
                .setToolbarColor(primaryColor)
                .build()

            val customTabsIntent = CustomTabsIntent.Builder()
                .setDefaultColorSchemeParams(colorScheme)
                .setShowTitle(true)
                .build()

            customTabsIntent.launchUrl(
                context,
                "https://oliviermarteaux.dev".toUri()
            )
        },
        modifier = modifier
    ) {
        Text("About the developer")
    }
}