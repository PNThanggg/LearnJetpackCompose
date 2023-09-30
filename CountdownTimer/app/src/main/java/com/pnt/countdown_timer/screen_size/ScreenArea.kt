package com.pnt.countdown_timer.screen_size

import android.graphics.Point

data class ScreenArea(
    val topLeft: Point,
    val topRight: Point,
    val bottomRight: Point,
    val bottomLeft: Point
) {

    companion object {

        @JvmStatic
        val None get() = ScreenArea(Point(0, 0), Point(0, 0), Point(0, 0), Point(0, 0))
    }

}