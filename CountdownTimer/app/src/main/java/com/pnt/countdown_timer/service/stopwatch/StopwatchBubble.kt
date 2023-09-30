package com.pnt.countdown_timer.service.stopwatch

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.pnt.countdown_timer.common.LocalHaloColour
import com.pnt.countdown_timer.common.PROGRESS_ARC_WIDTH
import com.pnt.countdown_timer.common.TIMER_SIZE_DP
import com.pnt.countdown_timer.common.TimeDisplay

@Composable
fun StopwatchBubble(modifier: Modifier, stopwatchState: StopwatchState) {
    Box(
        modifier = Modifier.run {
            then(modifier)
                .size(TIMER_SIZE_DP.dp)
                .padding(PROGRESS_ARC_WIDTH / 2)
        },
        contentAlignment = Alignment.Center
    ) {
        BorderArc(stopwatchState)
        TimeDisplay(stopwatchState.timeElapsed.intValue)
    }
}

@Composable
fun BorderArc(stopwatchState: StopwatchState) {
    var pausedAngle by remember { mutableFloatStateOf(210f) }
    var restartAngle by remember { mutableFloatStateOf(0f) }
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val haloColor = LocalHaloColour.current

    val animatedAngle by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 360f, animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing), repeatMode = RepeatMode.Restart
        ), label = ""
    )
    val drawAnimatedAngle by remember {
        derivedStateOf { pausedAngle + animatedAngle - restartAngle }
    }

    val running = stopwatchState.isRunningStateFlow.collectAsState()

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
            color = haloColor.copy(alpha = .1f),
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            style = Stroke(PROGRESS_ARC_WIDTH.toPx()),
            size = Size(size.width, size.height)
        )

        drawArc(
            color = haloColor,
            startAngle = if (!running.value) pausedAngle else drawAnimatedAngle,
            sweepAngle = 120f,
            useCenter = false,
            style = Stroke(PROGRESS_ARC_WIDTH.toPx()),
            size = Size(size.width, size.height)
        )
    }

    // should learn to write my own delegate for angle instead
    LaunchedEffect(Unit) {
        stopwatchState.isRunningStateFlow.collect {
            when (it) {
                true -> {
                    restartAngle = animatedAngle
                }

                false -> {
                    pausedAngle = drawAnimatedAngle
                }
            }
        }
    }
}