package com.dev.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import com.dev.designsystem.theme.colorBasicBg
import com.dev.designsystem.theme.colorDarkBg


@Composable
fun AppBackground(
    curveHeight: Float = 0.5f,
    curveDepth: Float = 300f,
    content: @Composable () -> Unit
) {
    Box(Modifier.fillMaxSize()) {

        Box(
            Modifier
                .fillMaxSize()
                .background(colorBasicBg)
        )

        Canvas(modifier = Modifier.fillMaxSize()) {

            val width = size.width
            val height = size.height * curveHeight

            val path = Path().apply {

                moveTo(0f, 0f)
                lineTo(0f, height)

                quadraticTo(
                    width / 2f,
                    height + curveDepth,
                    width,
                    height
                )

                lineTo(width, 0f)
                close()
            }

            drawPath(
                path = path,
                color = colorDarkBg
            )
        }

        content()
    }
}
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable

private fun AppBackgroundPreview() {
    AppBackground {
    }
}