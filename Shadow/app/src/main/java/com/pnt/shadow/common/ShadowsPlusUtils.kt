package com.pnt.shadow.common

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import com.pnt.shadow.rsblur.RSBlurShadowDefaults
import com.pnt.shadow.rsblur.rsBlurShadow
import com.pnt.shadow.softlayer.softLayerShadow

/**
 * Calculates shadow spread scale.
 *
 * @param spread The raw spread.
 * @param size The X or Y side.
 * @return The shadow spread scale.
 */
internal fun spreadScale(
    spread: Float,
    size: Float
): Float = 1.0F + ((spread / size) * 2.0F)

/**
 * A utility [Modifier] to apply ComposeShadowsPlus by [ShadowsPlusType].
 *
 * @param type The shadow type.
 * @param radius The shadow radius.
 * @param color The shadow color.
 * @param shape The shadow shape.
 * @param spread The shadow spread.
 * @param offset The shadow offset.
 * @param alignRadius The exponential align radius indicator.
 * @return The applied ComposeShadowsPlus [Modifier].
 */
fun Modifier.shadowsPlus(
    type: ShadowsPlusType = ShadowsPlusType.RSBlur,
    radius: Dp = ShadowsPlusDefaults.ShadowRadius,
    color: Color = ShadowsPlusDefaults.ShadowColor,
    shape: Shape = ShadowsPlusDefaults.ShadowShape,
    spread: Dp = ShadowsPlusDefaults.ShadowSpread,
    offset: DpOffset = ShadowsPlusDefaults.ShadowOffset,
    alignRadius: Boolean = RSBlurShadowDefaults.RSBlurShadowAlignRadius
): Modifier = this.then(
    when (type) {
        ShadowsPlusType.RSBlur -> {
            Modifier.rsBlurShadow(
                radius = radius,
                color = color,
                shape = shape,
                spread = spread,
                offset = offset,
                alignRadius = alignRadius
            )
        }
        ShadowsPlusType.SoftLayer -> {
            Modifier.softLayerShadow(
                radius = radius,
                color = color,
                shape = shape,
                spread = spread,
                offset = offset
            )
        }
    }
)