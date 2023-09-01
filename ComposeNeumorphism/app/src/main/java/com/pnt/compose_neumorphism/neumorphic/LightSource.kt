package com.pnt.compose_neumorphism.neumorphic

/**
 * Light source constants with some utils functions.
 */
enum class LightSource {
    TOP,
    BOTTOM,
    LEFT,
    RIGHT,
    LEFT_TOP,
    RIGHT_TOP,
    LEFT_BOTTOM,
    RIGHT_BOTTOM;

    fun opposite(): LightSource {
        return when (this) {
            TOP -> TOP
            BOTTOM -> BOTTOM
            LEFT -> LEFT
            RIGHT -> RIGHT
            LEFT_TOP -> RIGHT_BOTTOM
            RIGHT_TOP -> LEFT_BOTTOM
            LEFT_BOTTOM -> RIGHT_TOP
            RIGHT_BOTTOM -> LEFT_TOP
        }
    }
}
