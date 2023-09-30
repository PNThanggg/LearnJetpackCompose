package com.pnt.countdown_timer.tmp

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.content.res.Configuration
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.pnt.countdown_timer.R
import com.pnt.countdown_timer.common.NOTIFICATION_CHANNEL
import com.pnt.countdown_timer.screen_size.ScreenEz
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class MaccasService : Service() {
    private val job = SupervisorJob()
    val scope = CoroutineScope(Dispatchers.Default + job)

    private var isStarted = false

    lateinit var maccasOverlayController: MaccasOverlayController

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        ScreenEz.with(this.applicationContext)

        maccasOverlayController = MaccasOverlayController(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        postOngoingActivityNotification()
        return START_STICKY
    }

    private fun postOngoingActivityNotification() {
        if (!isStarted) {
            isStarted = true

            startForeground(MC.FOREGROUND_SERVICE_NOTIFICATION_ID, buildNotification())
        }
    }

    private fun buildNotification(): Notification {
//    val pendingIntent: PendingIntent =
//      Intent(this, com.pnt.countdown_timer.MainActivity::class.java).let { notificationIntent ->
//        PendingIntent.getActivity(this, 0, notificationIntent, FLAG_IMMUTABLE)
//      }

        return NotificationCompat.Builder(this, NOTIFICATION_CHANNEL)
            .setContentTitle("Floating Timer")
            .setSmallIcon(R.drawable.ic_alarm)
//        .setContentIntent(pendingIntent)
            .build()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        ScreenEz.refresh()
        maccasOverlayController.updateBubbleParamsWithinScreenBounds()
    }
}
