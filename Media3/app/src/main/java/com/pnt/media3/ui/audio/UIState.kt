package com.pnt.media3.ui.audio

sealed class UIState {
    object Initial : UIState()
    object Ready : UIState()
}