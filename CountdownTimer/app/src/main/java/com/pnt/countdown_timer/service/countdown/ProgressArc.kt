package com.pnt.countdown_timer.service.countdown

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import com.pnt.countdown_timer.common.LocalHaloColour
import com.pnt.countdown_timer.common.PROGRESS_ARC_WIDTH

@Composable
fun ProgressArc(timeLeftFraction: Float) {
    val sweepAngle = 360 * timeLeftFraction
    val haloColour = LocalHaloColour.current

    Canvas(
        Modifier.fillMaxSize()
    ) {
        drawCircle(
            color = Color.White,
        )

        drawArc(
            color = Color.White,
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            style = Stroke(PROGRESS_ARC_WIDTH.toPx()),
            size = Size(size.width, size.height)
        )

        drawArc(
            color = haloColour.copy(alpha = .1f),
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            style = Stroke(PROGRESS_ARC_WIDTH.toPx()),
            size = Size(size.width, size.height)
        )

        drawArc(
            color = haloColour,
            -90f,
            sweepAngle,
            false,
            style = Stroke(PROGRESS_ARC_WIDTH.toPx()),
            size = Size(size.width, size.height)
        )
    }
}