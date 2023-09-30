package com.pnt.countdown_timer.service.stopwatch

import androidx.compose.runtime.mutableIntStateOf
import com.pnt.countdown_timer.service.OverlayState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.timerTask

class StopwatchState(scope: CoroutineScope) {
    val overlayState = OverlayState()
    val timeElapsed = mutableIntStateOf(0)
    val isRunningStateFlow = MutableStateFlow(false)
    var stopwatchIncrementTask: TimerTask? = null

    fun resetStopwatchState() {
        timeElapsed.intValue = 0
        isRunningStateFlow.value = false
        stopwatchIncrementTask?.cancel()
        stopwatchIncrementTask = null
    }

    init {
        scope.launch {
            isRunningStateFlow.collect { running ->
                when (running) {
                    true -> {
                        stopwatchIncrementTask = timerTask {
                            timeElapsed.intValue++
                        }
                        Timer().scheduleAtFixedRate(stopwatchIncrementTask, 1000, 1000)
                    }

                    false -> {
                        stopwatchIncrementTask?.cancel()
                        stopwatchIncrementTask = null
                    }
                }
            }
        }
    }
}