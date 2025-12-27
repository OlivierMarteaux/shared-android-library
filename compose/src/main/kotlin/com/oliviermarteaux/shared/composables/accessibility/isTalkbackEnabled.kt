package com.oliviermarteaux.shared.composables.accessibility

import android.content.Context
import android.view.accessibility.AccessibilityManager
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun isTalkBackEnabled(): Boolean {
    val context = LocalContext.current
    val accessibilityManager =
        context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager

    // returns true when TalkBack (or any screen reader) is enabled
    return accessibilityManager.isEnabled &&
            accessibilityManager.isTouchExplorationEnabled
}