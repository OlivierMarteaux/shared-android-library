package com.oliviermarteaux.shared.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.oliviermarteaux.shared.compose.R

sealed class BottomNavItem(
    val screen: Screen,
    val icon: ImageVector,
    @param: StringRes val titleRes: Int
) {
    object Home : BottomNavItem(Screen.Home, Icons.Filled.Event, R.string.events)
    object Account : BottomNavItem(Screen.Account, Icons.Filled.Person, R.string.profile)
}