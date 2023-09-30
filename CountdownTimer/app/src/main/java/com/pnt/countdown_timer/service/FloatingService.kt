package com.pnt.countdown_timer.service

import android.app.Notification
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.app.Service
import android.content.Intent
import android.content.res.Configuration
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.pnt.countdown_timer.MainActivity
import com.pnt.countdown_timer.R
import com.pnt.countdown_timer.common.EXTRA_COUNTDOWN_DURATION
import com.pnt.countdown_timer.common.FOREGROUND_SERVICE_NOTIFICATION_ID
import com.pnt.countdown_timer.common.INTENT_COMMAND
import com.pnt.countdown_timer.common.INTENT_COMMAND_COUNTDOWN_COMPLETE
import com.pnt.countdown_timer.common.INTENT_COMMAND_COUNTDOWN_CREATE
import com.pnt.countdown_timer.common.INTENT_COMMAND_EXIT
import com.pnt.countdown_timer.common.INTENT_COMMAND_RESET
import com.pnt.countdown_timer.common.INTENT_COMMAND_STOPWATCH_CREATE
import com.pnt.countdown_timer.common.NOTIFICATION_CHANNEL
import com.pnt.countdown_timer.common.REQUEST_CODE_EXIT
import com.pnt.countdown_timer.common.REQUEST_CODE_RESET
import com.pnt.countdown_timer.screen_size.ScreenEz
import com.pnt.countdown_timer.service.countdown.TimerStateFinished
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class FloatingService : Service() {
    private val job = SupervisorJob()
    val scope = CoroutineScope(Dispatchers.Default + job)
    val state = ServiceState(scope)
//  private var isStarted = false

    private lateinit var overlayController: OverlayController
    private lateinit var alarmController: AlarmController

    override fun onCreate() {
        super.onCreate()

        ScreenEz.with(this.applicationContext)

        overlayController = OverlayController(this)
        alarmController = AlarmController(this)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        postOngoingActivityNotification()

        intent?.let {
            when (intent.getStringExtra(INTENT_COMMAND)) {
                INTENT_COMMAND_COUNTDOWN_CREATE -> {
                    val duration = intent.getIntExtra(EXTRA_COUNTDOWN_DURATION, 10)
                    state.countdownState.resetTimerState(duration)
                    state.countdownState.overlayState.isVisible.value = true
                }

                INTENT_COMMAND_COUNTDOWN_COMPLETE -> {
                    state.countdownState.timerState.value = TimerStateFinished
                }

                INTENT_COMMAND_STOPWATCH_CREATE -> {
                    state.stopwatchState.overlayState.isVisible.value = true
                }

                INTENT_COMMAND_EXIT -> {
                    if (state.countdownState.overlayState.isVisible.value == true) {
                        overlayController.exitCountdown()
                    }
                    if (state.stopwatchState.overlayState.isVisible.value == true) {
                        overlayController.exitStopwatch()
                    }
                    return START_NOT_STICKY
                }

                INTENT_COMMAND_RESET -> {
                    state.stopwatchState.resetStopwatchState()
                    state.countdownState.resetTimerState()
                }
            }
        }
        return START_STICKY
    }

    private fun postOngoingActivityNotification() {
        startForeground(FOREGROUND_SERVICE_NOTIFICATION_ID, buildNotification())
    }

    private fun buildNotification(): Notification {
        val pendingIntent: PendingIntent =
            Intent(this, MainActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(this, 0, notificationIntent, FLAG_IMMUTABLE)
            }

        val exitIntent = Intent(applicationContext, FloatingService::class.java)
        exitIntent.putExtra(INTENT_COMMAND, INTENT_COMMAND_EXIT)
        val exitPendingIntent = PendingIntent.getService(
            applicationContext,
            REQUEST_CODE_EXIT,
            exitIntent,
            FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
        )

        val resetIntent = Intent(applicationContext, FloatingService::class.java)
        resetIntent.putExtra(INTENT_COMMAND, INTENT_COMMAND_RESET)
        val resetPendingIntent = PendingIntent.getService(
            applicationContext,
            REQUEST_CODE_RESET,
            resetIntent,
            FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, NOTIFICATION_CHANNEL)
            .setContentTitle("Floating Timer")
            .setSmallIcon(R.drawable.ic_alarm).setContentIntent(pendingIntent).addAction(
                0, "Reset", resetPendingIntent
            ).addAction(
                0, "Exit", exitPendingIntent
            ).build()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        ScreenEz.refresh()
        scope.launch {
            state.configurationChanged.emit(Unit)
        }
    }
}