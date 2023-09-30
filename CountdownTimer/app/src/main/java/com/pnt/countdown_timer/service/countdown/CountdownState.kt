package com.pnt.countdown_timer.service.countdown

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import com.pnt.countdown_timer.service.OverlayState
import kotlinx.coroutines.flow.MutableStateFlow

class CountdownState {
    val overlayState = OverlayState()

    var durationSeconds: Int = 10
    var countdownSeconds by mutableIntStateOf(10)
    val timerState = MutableStateFlow<TimerState>(TimerStatePaused)

    fun resetTimerState(duration: Int = this.durationSeconds) {
        durationSeconds = duration
        countdownSeconds = duration
        timerState.value = TimerStatePaused
    }
}

sealed class TimerState

object TimerStateRunning : TimerState()
object TimerStatePaused : TimerState()
object TimerStateFinished : TimerState()
