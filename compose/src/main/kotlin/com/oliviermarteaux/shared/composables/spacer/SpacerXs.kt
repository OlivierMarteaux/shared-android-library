package com.oliviermarteaux.shared.composables.spacer

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.oliviermarteaux.shared.ui.theme.SharedPadding

/**
 * 8.dp
 */
@Composable
fun SpacerXs() = Spacer(modifier = Modifier.size(SharedPadding.xs))