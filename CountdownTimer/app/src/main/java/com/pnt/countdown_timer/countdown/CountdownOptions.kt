package com.pnt.countdown_timer.countdown

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pnt.countdown_timer.R
import com.pnt.countdown_timer.presentaion.viewmodels.HomeViewModel

@Composable
fun CountdownOptions() {
    val vm: HomeViewModel = viewModel()

    val vibration = vm.vibrationFlow.collectAsState(false)
    val sound = vm.soundFlow.collectAsState(false)

    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = vibration.value,
                onCheckedChange = {
                    vm.updateVibration(it)
                }
            )
            Text(stringResource(R.string.vibration))
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = sound.value,
                onCheckedChange = {
                    vm.updateSound(it)
                }
            )
            Text(stringResource(R.string.sound))
        }
    }
}