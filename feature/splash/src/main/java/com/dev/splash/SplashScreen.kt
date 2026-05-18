package com.dev.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import com.dev.designsystem.theme.PizzaAppTheme
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun SplashScreen(
    onFinish: () -> Unit,
    viewModel: SplashViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val state by viewModel.state.collectAsState()
    val progress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 2800, easing = FastOutSlowInEasing)
        )
        if (state is SplashUiState.Loading) viewModel.onAnimationFinished()
    }

    LaunchedEffect(state) {
        if (state is SplashUiState.Finished) {
            delay(400)
            onFinish()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    )

    PizzaReveal(progress = progress.value)
}


@Composable
fun PizzaReveal(progress: Float) {

    val pizzaBitmap = ImageBitmap.imageResource(id = com.dev.designsystem.R.drawable.pizza_splash)

    val sectorStartAngle = 43f
    val sectorSweep = 45f

    val step = 1f / 8

    Canvas(modifier = Modifier.fillMaxSize()) {

        val cx = size.width  / 2f
        val cy = size.height / 2f

        val pizzaDiameter = minOf(size.width, size.height) * 0.82f
        val pizzaRadius   = pizzaDiameter / 2f

        val flyDist = maxOf(size.width, size.height) * 1.2f

        val drawSize = pizzaDiameter.roundToInt()
        val topLeftX = (cx - pizzaRadius).roundToInt()
        val topLeftY = (cy - pizzaRadius).roundToInt()

        val clipRadius = pizzaDiameter * 1.5f

        repeat(8) { index ->

            val localP = ((progress - index * step) / step).coerceIn(0f, 1f)
            val eased  = FastOutSlowInEasing.transform(localP)

            if (eased <= 0f) return@repeat

            val sectorCenter = sectorStartAngle + index * sectorSweep + sectorSweep / 2f

            val rad  = Math.toRadians(sectorCenter.toDouble())
            val dirX = cos(rad).toFloat()
            val dirY = sin(rad).toFloat()

            val offsetX = dirX * flyDist * (1f - eased)
            val offsetY = dirY * flyDist * (1f - eased)

            val sectorPath = Path().apply {
                val pivotX = cx + offsetX
                val pivotY = cy + offsetY
                moveTo(pivotX, pivotY)
                val startRad = Math.toRadians((sectorStartAngle + index * sectorSweep).toDouble())
                val endRad   = Math.toRadians((sectorStartAngle + (index + 1) * sectorSweep).toDouble())
                lineTo(
                    pivotX + cos(startRad).toFloat() * clipRadius,
                    pivotY + sin(startRad).toFloat() * clipRadius
                )
                arcTo(
                    rect = androidx.compose.ui.geometry.Rect(
                        left   = pivotX - clipRadius,
                        top    = pivotY - clipRadius,
                        right  = pivotX + clipRadius,
                        bottom = pivotY + clipRadius
                    ),
                    startAngleDegrees = sectorStartAngle + index * sectorSweep,
                    sweepAngleDegrees = sectorSweep,
                    forceMoveTo = false
                )
                close()
            }

            clipPath(sectorPath) {
                drawImage(
                    image     = pizzaBitmap,
                    dstOffset = IntOffset(
                        x = (cx + offsetX - pizzaRadius).roundToInt(),
                        y = (cy + offsetY - pizzaRadius).roundToInt()
                    ),
                    dstSize = IntSize(drawSize, drawSize),
                    alpha   = eased,
                )
            }
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PizzaFullPreview() {
    PizzaAppTheme { PizzaReveal(progress = 1f) }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PizzaHalfPreview() {
    PizzaAppTheme { PizzaReveal(progress = 0.5f) }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PizzaStartPreview() {
    PizzaAppTheme { PizzaReveal(progress = 0.15f) }
}