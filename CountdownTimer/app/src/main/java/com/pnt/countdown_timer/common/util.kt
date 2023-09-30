package com.pnt.countdown_timer.common

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

// need a better place to keep this
val LocalHaloColour = compositionLocalOf<Color> {
  error("CompositionLocal com.pnt.countdown_timer.common.getLocalHaloColour not present")
}