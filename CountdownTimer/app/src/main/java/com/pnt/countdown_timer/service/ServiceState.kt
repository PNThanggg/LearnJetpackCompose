package com.pnt.countdown_timer.service

import com.pnt.countdown_timer.service.countdown.CountdownState
import com.pnt.countdown_timer.service.stopwatch.StopwatchState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow

class ServiceState(scope: CoroutineScope) {
    val countdownState = CountdownState()
    val stopwatchState = StopwatchState(scope)

    val configurationChanged = MutableSharedFlow<Unit>()
}