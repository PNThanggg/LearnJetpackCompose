package com.pnt.shadow

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.pnt.shadow.softlayer.SoftLayerShadowContainer
import com.pnt.shadow.softlayer.softLayerShadow

@Composable
fun Shadow() {
    SoftLayerShadowContainer {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .fillMaxSize()
                .padding(all = 64.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .softLayerShadow(
                        radius = 10.dp,
                        color = Color.Black.copy(alpha = 0.233F),
                        shape = CircleShape,
                        spread = 10.dp,
                        offset = DpOffset(
                            x = 0.dp,
                            y = 0.dp
                        )
                    )

                    .background(
                        color = Color.White,
                        shape = CircleShape
                    )
            )
        }
    }
}

@Preview
@Composable
fun PreviewShadow() {
    Shadow()
}