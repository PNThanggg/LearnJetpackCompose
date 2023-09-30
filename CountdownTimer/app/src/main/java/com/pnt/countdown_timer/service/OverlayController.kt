package com.pnt.countdown_timer.service

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PixelFormat
import android.util.Log
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.WindowManager
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import com.pnt.countdown_timer.common.LocalHaloColour
import com.pnt.countdown_timer.common.TIMER_SIZE_PX
import com.pnt.countdown_timer.presentaion.viewmodels.OverlayViewHolder
import com.pnt.countdown_timer.providePreferencesRepository
import com.pnt.countdown_timer.screen_size.ScreenEz
import com.pnt.countdown_timer.service.countdown.CountdownBubble
import com.pnt.countdown_timer.service.countdown.TimerStateFinished
import com.pnt.countdown_timer.service.countdown.TimerStatePaused
import com.pnt.countdown_timer.service.countdown.TimerStateRunning
import com.pnt.countdown_timer.service.stopwatch.StopwatchBubble
import com.pnt.countdown_timer.service.stopwatch.StopwatchState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlin.math.max
import kotlin.math.min

class OverlayController(val service: FloatingService) {
    private val countdownState = service.state.countdownState
    private val countdownIsVisible: Flow<Boolean?>
        get() = countdownState.overlayState.isVisible

    private val stopwatchState = service.state.stopwatchState
    private val stopwatchIsVisible: Flow<Boolean?>
        get() = stopwatchState.overlayState.isVisible

    val windowManager = service.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    init {
        initViewControllers()
    }

    private fun createFullscreenOverlay(overlayContent: @Composable () -> Unit): OverlayViewHolder {
        val fullscreenOverlay = OverlayViewHolder(
            WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
//        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
            ), service
        )

        fullscreenOverlay.view.setContent {
            overlayContent()
        }

        return fullscreenOverlay
    }

    private fun createCountdownClickTarget(): OverlayViewHolder {
        return createTimerBubble(
            countdownState.overlayState,
            bubble = { CountdownBubble(countdownState) },
            exitTimer = { exitCountdown() },
            onDoubleTap = { countdownState.resetTimerState() },
            onTap = {
                when (countdownState.timerState.value) {
                    is TimerStatePaused -> {
                        countdownState.timerState.value = TimerStateRunning
                    }

                    is TimerStateRunning -> {
                        countdownState.timerState.value = TimerStatePaused
                    }

                    is TimerStateFinished -> {
                        countdownState.resetTimerState(countdownState.durationSeconds)
                    }
                }
            }
        )
    }

    private fun createTimerBubble(
        overlayState: OverlayState,
        bubble: @Composable () -> Unit,
        exitTimer: () -> Unit,
        onDoubleTap: () -> Unit,
        onTap: () -> Unit
    ): OverlayViewHolder {
        val viewHolder = OverlayViewHolder(
            WindowManager.LayoutParams(
                TIMER_SIZE_PX,
                TIMER_SIZE_PX,
                0,
                0,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
            ), service
        )

        viewHolder.view.setContent {
            val haloColour =
                service.application.providePreferencesRepository().haloColourFlow.collectAsState(
                    initial = MaterialTheme.colorScheme.primary
                )

            CompositionLocalProvider(LocalHaloColour provides haloColour.value) {
                bubble()
            }

            LaunchedEffect(Unit) {
                service.state.configurationChanged.collect {
                    updateClickTargetParamsWithinScreenBounds(
                        viewHolder,
                        overlayState
                    )
                }
            }
        }
        clickTargetSetOnTouchListener(
            viewHolder,
            service.state.stopwatchState.overlayState,
            exitTimer,
            onDoubleTap,
            onTap
        )
        return viewHolder
    }

    private fun createStopwatchClickTarget(): OverlayViewHolder {
        return createTimerBubble(
            stopwatchState.overlayState,
            bubble = { StopwatchBubble(Modifier, stopwatchState) },
            exitTimer = { exitStopwatch() },
            onDoubleTap = { stopwatchState.resetStopwatchState() },
            onTap = { onClickStopwatchClickTarget(stopwatchState) }
        )
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun clickTargetSetOnTouchListener(
        viewHolder: OverlayViewHolder,
        overlayState: OverlayState,
        exitTimer: () -> Unit,
        onDoubleTap: () -> Unit,
        onTap: () -> Unit
    ) {

        var paramStartDragX = 0
        var paramStartDragY = 0
        var startDragRawX = 0F
        var startDragRawY = 0F

        val tapDetector = GestureDetector(service, object : SimpleOnGestureListener() {
            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                onTap()
                return true
            }

            override fun onDoubleTap(e: MotionEvent): Boolean {
                onDoubleTap()
                return true
            }
        })

        viewHolder.view.setOnTouchListener { _, event ->
            if (tapDetector.onTouchEvent(event)) {
                // just to be safe
                overlayState.isDragging.value = false
                return@setOnTouchListener true
            }

            val params = viewHolder.params
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {

                    paramStartDragX = params.x
                    paramStartDragY = params.y
                    startDragRawX = event.rawX
                    startDragRawY = event.rawY
                }

                MotionEvent.ACTION_MOVE -> {
                    overlayState.isDragging.value = true
                    params.x = (paramStartDragX + (event.rawX - startDragRawX)).toInt()
                    params.y = (paramStartDragY + (event.rawY - startDragRawY)).toInt()
                    updateClickTargetParamsWithinScreenBounds(viewHolder, overlayState)
                }

                MotionEvent.ACTION_UP -> {
                    overlayState.isDragging.value = false
                    if (overlayState.isTimerHoverTrash) {
                        overlayState.isTimerHoverTrash = false
                        exitTimer()
                    }
                }
            }
            false
        }
    }

    private fun updateClickTargetParamsWithinScreenBounds(
        viewHolder: OverlayViewHolder,
        overlayState: OverlayState
    ) {
        val params = viewHolder.params
        var x = params.x
        var y = params.y
        x = max(x, 0)
        x = min(x, ScreenEz.safeWidth - TIMER_SIZE_PX)
        y = max(y, 0)
        y = min(y, ScreenEz.safeHeight - TIMER_SIZE_PX)
        params.x = x
        params.y = y
        try {
            windowManager.updateViewLayout(viewHolder.view, params)
        } catch (e: IllegalArgumentException) {
            // this was happening in prod, can't reproduce
            Log.e(
                "com.pnt.countdown_timer.service.OverlayController",
                "IllegalArgumentException: $e"
            )
        }
        overlayState.timerOffset = IntOffset(params.x, params.y)
    }

    private fun initViewControllers() {
        OverlayViewController(
            this::createCountdownClickTarget,
            countdownIsVisible.filterNotNull(),
            windowManager,
            service.scope
        )
        OverlayViewController(
            {
                createFullscreenOverlay {
                    IsDraggingOverlay(service.state.countdownState.overlayState)
                }
            },
            service.state.countdownState.overlayState.isDragging,
            windowManager, service.scope
        )

        OverlayViewController(
            this::createStopwatchClickTarget,
            stopwatchIsVisible.filterNotNull(),
            windowManager,
            service.scope
        )
        OverlayViewController(
            {
                createFullscreenOverlay {
                    IsDraggingOverlay(service.state.stopwatchState.overlayState)
                }
            },
            service.state.stopwatchState.overlayState.isDragging,
            windowManager, service.scope
        )
    }

    fun exitCountdown() {
        countdownState.overlayState.isVisible.value = false
        countdownState.overlayState.reset()
        countdownState.resetTimerState()
        exitIfNoTimers()
    }

    private fun exitIfNoTimers() {
        val countdownIsVisible = countdownState.overlayState.isVisible.value
        val stopwatchIsVisible = stopwatchState.overlayState.isVisible.value
        if (countdownIsVisible != true && stopwatchIsVisible != true) {
            service.stopSelf()
        }
    }

    fun exitStopwatch() {
        val stopwatchState = service.state.stopwatchState
        stopwatchState.overlayState.isVisible.value = false
        stopwatchState.overlayState.reset()
        stopwatchState.resetStopwatchState()
        exitIfNoTimers()
    }
}

fun onClickStopwatchClickTarget(stopwatchState: StopwatchState) {
    val running = stopwatchState.isRunningStateFlow
    running.value = !running.value
}