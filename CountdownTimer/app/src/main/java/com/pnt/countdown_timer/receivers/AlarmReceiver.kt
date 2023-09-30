package com.pnt.countdown_timer.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.pnt.countdown_timer.common.INTENT_COMMAND
import com.pnt.countdown_timer.common.INTENT_COMMAND_COUNTDOWN_COMPLETE
import com.pnt.countdown_timer.service.FloatingService

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        Log.d("AlarmReceiver", "onReceive start")
        Log.d("AlarmReceiver", "context $context")

        sendCommandService(context)
    }

    private fun sendCommandService(context: Context) {
        val intent = Intent(context.applicationContext, FloatingService::class.java)
        intent.putExtra(INTENT_COMMAND, INTENT_COMMAND_COUNTDOWN_COMPLETE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        }
    }
}