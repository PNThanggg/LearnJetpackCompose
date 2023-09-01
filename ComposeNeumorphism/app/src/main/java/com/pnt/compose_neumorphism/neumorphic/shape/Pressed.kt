package com.pnt.compose_neumorphism.neumorphic.shape

import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import com.pnt.compose_neumorphism.neumorphic.NeuStyle
import com.pnt.compose_neumorphism.neumorphic.drawForegroundShadows

class Pressed(override val cornerShape: CornerShape) : NeuShape(cornerShape) {
    override fun draw(drawScope: ContentDrawScope, style: NeuStyle) {
        drawScope.drawContent()
        drawScope.drawForegroundShadows(this, style)
    }
}