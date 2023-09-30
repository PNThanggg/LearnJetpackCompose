package com.pnt.countdown_timer.presentaion.viewmodels

import android.view.Gravity
import android.view.WindowManager
import com.pnt.countdown_timer.service.FloatingService
import com.pnt.countdown_timer.service.overlayViewFactory

class OverlayViewHolder(val params: WindowManager.LayoutParams, service: FloatingService) {
    val view = overlayViewFactory(service)

    init {
        params.gravity = Gravity.TOP or Gravity.LEFT
    }
}