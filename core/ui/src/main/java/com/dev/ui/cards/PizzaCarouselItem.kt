package com.dev.ui.cards

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChanged
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.dev.model.Pizza
import androidx.compose.ui.unit.IntSize

@Composable
fun PizzaCarouselItem(
    pizza: Pizza,
    isCurrent: Boolean,
    selectedSize: String,
    onClick: () -> Unit = {},
) {

    val sizeDp by animateDpAsState(
        targetValue = when {
            !isCurrent -> 220.dp
            selectedSize == "S" -> 235.dp
            selectedSize == "L" -> 300.dp
            else -> 260.dp
        },
        animationSpec = spring(
            stiffness = 300f,
            dampingRatio = 0.78f
        ),
        label = "pizzaSize"
    )

    var isFullscreen by remember(pizza.id) { mutableStateOf(false) }

    Box(

        modifier = Modifier
            .width(300.dp)
            .height(320.dp),
        contentAlignment = Alignment.Center

    ) {
        AsyncImage(
            model = pizza.imageUrl,
            contentDescription = pizza.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(sizeDp)
                .clip(CircleShape)
                .clickable { onClick() },
        )

        if (isCurrent) {

            Icon(

                imageVector = Icons.Default.Search,
                contentDescription = "Zoom",
                tint = Color.White,
                modifier = Modifier
                    .size(40.dp)
                    .clickable { isFullscreen = true }

            )

        }
    }

    if (isFullscreen) {
        PizzaFullscreenOverlay(
            imageUrl = pizza.imageUrl,
            name = pizza.name,
            onDismiss = { isFullscreen = false },
        )
    }
}

@Composable
private fun PizzaFullscreenOverlay(
    imageUrl: String,
    name: String,
    onDismiss: () -> Unit,
) {
    var zoom by remember { mutableFloatStateOf(1f) }
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    var size by remember { mutableStateOf(IntSize.Zero) }

    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
    }

    val scaleAnim by animateFloatAsState(
        targetValue = if (visible) 1f else 0.65f,
        animationSpec = spring(
            dampingRatio = 0.72f,
            stiffness = 700f
        ),
        label = "scale"
    )

    val alphaAnim by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(160),
        label = "alpha"
    )

    Dialog(
        onDismissRequest = {
            visible = false
            onDismiss()
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false,
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .onSizeChanged { size = it }
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    visible = false
                    onDismiss()
                },
            contentAlignment = Alignment.Center
        ) {

            AsyncImage(
                model = imageUrl,
                contentDescription = name,
                contentScale = ContentScale.Crop,

                modifier = Modifier
                    .fillMaxSize()

                    .pointerInput(Unit) {
                        awaitEachGesture {
                            awaitFirstDown(requireUnconsumed = false)

                            do {
                                val event = awaitPointerEvent()

                                if (event.changes.size >= 2) {

                                    val zoomChange = event.calculateZoom()
                                    val panChange = event.calculatePan()

                                    zoom = (zoom * zoomChange).coerceIn(1f, 30f)

                                    val maxOff = (size.width * (zoom - 1f)) / 2f

                                    offsetX = (offsetX + panChange.x)
                                        .coerceIn(-maxOff, maxOff)

                                    offsetY = (offsetY + panChange.y)
                                        .coerceIn(-maxOff, maxOff)

                                    event.changes.forEach {
                                        if (it.positionChanged()) it.consume()
                                    }
                                }

                            } while (event.changes.any { it.pressed })
                        }
                    }

                    .graphicsLayer {
                        scaleX = zoom * scaleAnim
                        scaleY = zoom * scaleAnim
                        translationX = offsetX
                        translationY = offsetY
                        alpha = alphaAnim
                    }
            )
        }
    }
}