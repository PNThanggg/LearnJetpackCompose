package com.pnt.caster.presentation.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun CasterTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = CasterColors,
        typography = CasterTypography,
        shapes = CasterShapes,
        content = content
    )
}
