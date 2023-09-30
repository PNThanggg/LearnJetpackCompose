package com.pnt.countdown_timer.tmp

import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun MaccasScreen() {
    val context = LocalContext.current

    Column {
        Button(onClick = {
            startMaccasService(context)
        }) {
            Text("start service")
        }
    }
}

fun startMaccasService(context: Context) {
    val intent = Intent(context.applicationContext, MaccasService::class.java)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        context.startForegroundService(intent)
    } else {
        Toast.makeText(context, "Version code < 0", Toast.LENGTH_LONG).show()
    }
}

