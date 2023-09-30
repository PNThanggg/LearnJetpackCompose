package com.pnt.countdown_timer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pnt.countdown_timer.common.BillingClientWrapper
import com.pnt.countdown_timer.presentaion.screens.HomeScreen
import com.pnt.countdown_timer.presentaion.screens.SettingsScreen
import com.pnt.countdown_timer.presentaion.ui.theme.CountdownTimerTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preferencesRepository = application.providePreferencesRepository()
        lifecycleScope.launch {
            BillingClientWrapper.run(application) {
                val purchased = it.checkHaloColourPurchased()
                preferencesRepository.updateHaloColourPurchased(purchased)
                if (!purchased) {
                    preferencesRepository.resetHaloColour()
                }
            }
        }

        setContent {
            CountdownTimerTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                ) {
                    FloatingTimerNavHost()
                }
            }
        }
    }
}

val LocalNavController = compositionLocalOf<NavHostController> {
    error("CompositionLocal com.pnt.countdown_timer.getLocalNavController not present")
}

@Composable
fun FloatingTimerNavHost(
) {
    val navController = rememberNavController()

    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(
            navController = navController, startDestination = "home"
        ) {
            composable("home") {
                HomeScreen()
            }

            composable("settings") {
                SettingsScreen()
            }
        }
    }
}