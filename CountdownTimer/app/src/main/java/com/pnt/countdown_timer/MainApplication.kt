package com.pnt.countdown_timer

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.pnt.countdown_timer.common.NOTIFICATION_CHANNEL
import com.pnt.countdown_timer.common.NOTIFICATION_CHANNEL_DISPLAY
import com.pnt.countdown_timer.data.PreferencesRepository
import com.pnt.countdown_timer.data.dataStore


class MainApplication : Application() {

    lateinit var preferencesRepository: PreferencesRepository

    override fun onCreate() {
        super.onCreate()
        preferencesRepository = PreferencesRepository(dataStore)
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val notificationChannel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                NOTIFICATION_CHANNEL,
                NOTIFICATION_CHANNEL_DISPLAY,
                NotificationManager.IMPORTANCE_DEFAULT
            )
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        // channel description???
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(notificationChannel)
    }
}

fun Application.providePreferencesRepository(): PreferencesRepository {
    return (this as MainApplication).preferencesRepository
}