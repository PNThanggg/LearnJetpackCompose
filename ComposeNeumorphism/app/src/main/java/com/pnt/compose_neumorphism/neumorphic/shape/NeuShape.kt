package com.pnt.compose_neumorphism.neumorphic.shape

import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import com.pnt.compose_neumorphism.neumorphic.NeuStyle

abstract class NeuShape(open val cornerShape: CornerShape) {
    abstract fun draw(drawScope: ContentDrawScope, style: NeuStyle)
}