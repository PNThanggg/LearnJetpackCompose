package com.pnt.countdown_timer.tmp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

@Composable
fun Fullscreen() {
    val controller = LocalMaccasOverlayController.current

    Box(
        modifier = Modifier
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text("offset ${controller.bubbleState.offsetPx}")
            DDTrash()
        }
    }
}

@Composable
fun DDTrash() {
    val controller = LocalMaccasOverlayController.current

    val context = LocalContext.current
    val timerSizePx = remember {
        val density = context.resources.displayMetrics.density
        MC.OVERLAY_SIZE_DP * density
    }
    var trashRect by remember { mutableStateOf(Rect.Zero) }

    val isTimerDragHoveringTrash = remember {
        derivedStateOf {
            calcBubbleIsHoverTrash(controller.bubbleState.offsetPx, timerSizePx, trashRect)
        }
    }

    val backgroundColor by remember {
        derivedStateOf {
            if (isTimerDragHoveringTrash.value) {
                Color.Red
            } else {
                Color.Green
            }
        }
    }



    Box(
        modifier = Modifier
            .size(100.dp)
            .background(backgroundColor)
            .onGloballyPositioned {
                trashRect = it.boundsInRoot()
            }
    )
}

fun calcBubbleIsHoverTrash(
    offset: IntOffset,
    bubbleSizePx: Float,
    trashRect: Rect
): Boolean {
    val timerCenterX = offset.x + (bubbleSizePx / 2)
    val timerCenterY = offset.y + (bubbleSizePx / 2)
    return !(timerCenterX < trashRect.left ||
            timerCenterX > trashRect.right ||
            timerCenterY < trashRect.top ||
            timerCenterY > trashRect.bottom)
}
