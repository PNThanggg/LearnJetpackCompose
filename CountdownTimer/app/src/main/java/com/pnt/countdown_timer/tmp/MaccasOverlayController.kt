package com.pnt.countdown_timer.tmp

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.MotionEvent
import android.view.WindowManager
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.IntOffset
import com.pnt.countdown_timer.screen_size.ScreenEz
import com.pnt.countdown_timer.service.overlayViewFactory
import com.pnt.countdown_timer.tmp.dragdrop.DDBubble
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.min

val LocalMaccasOverlayController = compositionLocalOf<MaccasOverlayController> {
    error("CompositionLocal com.pnt.countdown_timer.tmp.getLocalMaccasOverlayController not present")
}


class MaccasOverlayController(val service: MaccasService) {

    val bubbleParams: WindowManager.LayoutParams = initBubbleLayoutParams(service)
    val bubbleView: ComposeView = overlayViewFactory(service)

    val bubbleState = MaccasBubbleState()

    val fullscreenParams: WindowManager.LayoutParams
    var fullscreenView: ComposeView? = null

    val windowManager = service.getSystemService(Context.WINDOW_SERVICE) as WindowManager


    init {
        setContentBubbleView()

        fullscreenParams = initFullscreenLayoutParams()

        windowManager.addView(bubbleView, bubbleParams)

        watchIsBubbleDragging()
    }

    private fun watchIsBubbleDragging() {
        service.scope.launch(Dispatchers.Main) {
            bubbleState.isDragging.collect {
                when (it) {
                    true -> {
                        fullscreenView = createFullscreenView()
                        windowManager.addView(fullscreenView, fullscreenParams)
                    }

                    false -> {
                        windowManager.removeView(fullscreenView)
                    }

                    null -> {}
                }
            }
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun setContentBubbleView() {
        bubbleView.setContent {
            CompositionLocalProvider(LocalMaccasOverlayController provides this) {
                DDBubble()
            }
        }

        var paramStartDragX = 0
        var paramStartDragY = 0
        var startDragRawX = 0F
        var startDragRawY = 0F

        bubbleView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    paramStartDragX = bubbleParams.x
                    paramStartDragY = bubbleParams.y
                    startDragRawX = event.rawX
                    startDragRawY = event.rawY
                }

                MotionEvent.ACTION_MOVE -> {
                    bubbleState.isDragging.value = true

                    bubbleParams.x = (paramStartDragX + (event.rawX - startDragRawX)).toInt()
                    bubbleParams.y = (paramStartDragY + (event.rawY - startDragRawY)).toInt()

                    updateBubbleParamsWithinScreenBounds()
                }

                MotionEvent.ACTION_UP -> {
                    bubbleState.isDragging.value = false
                }

            }
            false
        }

    }

    private fun createFullscreenView(): ComposeView {
        val view = overlayViewFactory(service)
        view.setContent {
            CompositionLocalProvider(LocalMaccasOverlayController provides this) {
                Fullscreen()
            }
        }

        return view
    }

    // refactor for countdown or stopwatch
    fun updateBubbleParamsWithinScreenBounds() {
        var x = bubbleParams.x.toFloat()
        var y = bubbleParams.y.toFloat()
        x = max(x, 0f)
        x = min(x, ScreenEz.safeWidth - MC.OVERLAY_SIZE_PX)

        y = max(y, 0f)
        y = min(y, ScreenEz.safeHeight - MC.OVERLAY_SIZE_PX)

        bubbleParams.x = x.toInt()
        bubbleParams.y = y.toInt()

        windowManager.updateViewLayout(bubbleView, bubbleParams)
        bubbleState.offsetPx = IntOffset(bubbleParams.x, bubbleParams.y)
    }

}

private fun initBubbleLayoutParams(context: Context): WindowManager.LayoutParams {
    val density = context.resources.displayMetrics.density
    val overlaySizePx = (MC.OVERLAY_SIZE_DP * density).toInt()
    val params = WindowManager.LayoutParams(
        overlaySizePx,
        overlaySizePx,
        0,
        0,
        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
        PixelFormat.TRANSLUCENT
    )
    params.gravity = Gravity.TOP or Gravity.LEFT
    return params
}


private fun initFullscreenLayoutParams(): WindowManager.LayoutParams {
    val params = WindowManager.LayoutParams(
        WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
        PixelFormat.TRANSLUCENT
    )
    params.gravity = Gravity.TOP or Gravity.LEFT
    return params
}