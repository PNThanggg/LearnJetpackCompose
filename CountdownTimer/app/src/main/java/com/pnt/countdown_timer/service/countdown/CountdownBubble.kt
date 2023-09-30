package com.pnt.countdown_timer.service.countdown

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.pnt.countdown_timer.common.PROGRESS_ARC_WIDTH
import com.pnt.countdown_timer.common.TIMER_SIZE_DP
import com.pnt.countdown_timer.common.TimeDisplay

@Composable
fun CountdownBubble(countdownState: CountdownState) {
    val timeLeftFraction =
        countdownState.countdownSeconds / countdownState.durationSeconds.toFloat()
    Box(
        modifier = Modifier
          .size(TIMER_SIZE_DP.dp)
          .padding(PROGRESS_ARC_WIDTH / 2)
          .zIndex(1f),
        contentAlignment = Alignment.Center
    ) {
        ProgressArc(timeLeftFraction)
        TimeDisplay(countdownState.countdownSeconds)
    }
}