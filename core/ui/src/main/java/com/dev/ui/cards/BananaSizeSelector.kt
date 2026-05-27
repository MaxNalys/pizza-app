package com.dev.ui.cards

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dev.designsystem.R
import com.dev.model.Pizza

@Composable
fun BananaSizeSelector(
    pizza: Pizza,
    selectedSize: String,
    onSizeSelected: (String) -> Unit,
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp),
        contentAlignment = Alignment.TopCenter
    ) {

        BananaWithCurvedLabel(
            modifier = Modifier.padding(top = 0.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 46.dp)
        ) {
            val sizes = listOf("S", "M", "L")

            sizes.forEach { size ->
                val variantExists = pizza.variants.any { it.size.uppercase() == size }

                val (alignment, offsetY) = when (size) {
                    "S" -> Alignment.BottomStart to (-50).dp
                    "M" -> Alignment.BottomCenter to 0.dp
                    "L" -> Alignment.BottomEnd to (-50).dp
                    else -> Alignment.BottomCenter to 0.dp
                }

                Box(
                    modifier = Modifier
                        .align(alignment)
                        .offset(y = offsetY)
                ) {
                    SizeButton(
                        label = sizeLabel(size),
                        isSelected = size == selectedSize.uppercase(),
                        onClick = { if (variantExists) onSizeSelected(size) },
                    )
                }
            }
        }
    }
}

@Composable
private fun BananaWithCurvedLabel(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CurvedText(
            text = stringResource(R.string.banana_for_scale),
            modifier = Modifier
                .width(220.dp)
                .height(45.dp)
        )

        Image(
            painter = painterResource(R.drawable.banana),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .offset(y = (-10).dp)
                .size(width = 85.dp, height = 50.dp)
                .graphicsLayer {
                    rotationZ = 180f
                }
        )
    }
}

@Composable
private fun sizeLabel(size: String): String = when (size) {
    "S" -> stringResource(R.string.size_small)
    "M" -> stringResource(R.string.size_medium)
    "L" -> stringResource(R.string.size_large)
    else -> size
}

@Composable
private fun CurvedText(
    text: String,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val paint = android.graphics.Paint().apply {
            color = android.graphics.Color.parseColor("#5A5A5A")
            textSize = 34f
            typeface = android.graphics.Typeface.create(
                android.graphics.Typeface.DEFAULT,
                android.graphics.Typeface.NORMAL
            )
            textAlign = android.graphics.Paint.Align.CENTER
            isAntiAlias = true
            letterSpacing = 0.04f
        }

        val path = android.graphics.Path()
        val width = size.width
        val height = size.height

        path.addArc(
            0f,
            height * 0.3f,
            width,
            height * 2.5f,
            180f,
            180f
        )

        drawContext.canvas.nativeCanvas.drawTextOnPath(
            text,
            path,
            0f,
            8f,
            paint
        )
    }
}