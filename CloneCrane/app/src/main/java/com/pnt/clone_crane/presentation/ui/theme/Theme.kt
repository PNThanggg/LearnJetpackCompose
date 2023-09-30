package com.pnt.clone_crane.presentation.ui.theme

import androidx.compose.runtime.Composable

@Composable
fun CraneTheme(content: @Composable () -> Unit) {
    androidx.compose.material.MaterialTheme(colors = craneColors, typography = craneTypography) {
        content()
    }
}
