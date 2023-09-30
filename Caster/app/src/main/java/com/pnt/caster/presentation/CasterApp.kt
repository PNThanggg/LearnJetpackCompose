package com.pnt.caster.presentation

import android.app.Activity
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.window.layout.DisplayFeature
import com.pnt.caster.R
import com.pnt.caster.loadInterstitial
import com.pnt.caster.presentation.home.Home
import com.pnt.caster.presentation.player.PlayerScreen
import com.pnt.caster.presentation.player.PlayerViewModel
import com.pnt.caster.showInterstitial

private const val TAG = "CasterApp"

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CasterApp(
    windowSizeClass: WindowSizeClass,
    displayFeatures: List<DisplayFeature>,
    appState: CasterAppState = rememberCasterAppState()
) = if (appState.isOnline) {
    val activity = LocalContext.current as Activity

    activity.loadInterstitial()

    NavHost(
        navController = appState.navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) { backStackEntry ->
            Home(
                navigateToPlayer = { episodeUri ->
                    appState.navigateToPlayer(episodeUri, backStackEntry)
                }
            )
        }

        composable(Screen.Player.route) { backStackEntry ->
            val playerViewModel: PlayerViewModel = viewModel(
                factory = PlayerViewModel.provideFactory(
                    owner = backStackEntry,
                    defaultArgs = backStackEntry.arguments
                )
            )

            PlayerScreen(
                playerViewModel,
                windowSizeClass,
                displayFeatures,
                onBackPress = appState::navigateBack
            )
        }
    }
} else {
    OfflineDialog { appState.refreshOnline() }
}

@Composable
fun OfflineDialog(onRetry: () -> Unit) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text(text = stringResource(R.string.connection_error_title)) },
        text = { Text(text = stringResource(R.string.connection_error_message)) },
        confirmButton = {
            TextButton(onClick = onRetry) {
                Text(stringResource(R.string.retry_label))
            }
        }
    )
}
