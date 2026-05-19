package com.dev.ui.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.dev.designsystem.constants.Paddings
import com.dev.designsystem.icon.PizzaAppIcons

@Composable
fun PizzaHeader(
    name: String,
    onBackClick: () -> Unit = {},
    onFavoriteClick: () -> Unit = {},
    iconAnimationProgress: Float = 1f
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Paddings.LARGE_PADDING, vertical = Paddings.MEDIUM_PADDING),
    ) {

        val backButtonTranslationX = (1f - iconAnimationProgress) * -120f

        CircleIconButton(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .graphicsLayer {
                    translationX = backButtonTranslationX
                    alpha = iconAnimationProgress
                    scaleX = 0.6f + (iconAnimationProgress * 0.4f)
                    scaleY = 0.6f + (iconAnimationProgress * 0.4f)
                },
            imageVector = PizzaAppIcons.ArrowBack,
            contentDescription = "Back",
            onClick = onBackClick
        )

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer { alpha = iconAnimationProgress },
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Pizzas",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF9E9E9E),
            )
            Text(
                text = name,
                style = MaterialTheme.typography.displayMedium,
                color = Color(0xFF1E1E1E),
            )
        }

        val favoriteButtonTranslationX = (1f - iconAnimationProgress) * 120f

        CircleIconButton(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .graphicsLayer {
                    translationX = favoriteButtonTranslationX
                    alpha = iconAnimationProgress
                    scaleX = 0.6f + (iconAnimationProgress * 0.4f)
                    scaleY = 0.6f + (iconAnimationProgress * 0.4f)
                },
            imageVector = PizzaAppIcons.Favorite,
            contentDescription = "Like",
            onClick = onFavoriteClick
        )
    }
}

@Composable
internal fun CircleIconButton(
    modifier: Modifier,
    imageVector: ImageVector,
    contentDescription: String,
    animationProgress: Float = 1f,
    onClick: () -> Unit
) {
    val alpha = animationProgress
    val scale = 0.7f + (animationProgress * 0.3f)
    val translateY = (1f - animationProgress) * 40f

    Box(
        modifier = modifier
            .graphicsLayer {
                this.alpha = alpha
                this.scaleX = scale
                this.scaleY = scale
                this.translationY = translateY
            }
            .size(44.dp)
            .shadow(
                elevation = 4.dp,
                shape = CircleShape,
                ambientColor = Color.Black.copy(alpha = 0.15f),
                spotColor = Color.Black.copy(alpha = 0.15f)
            )
            .clip(CircleShape)
            .background(Color.White)
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            modifier = Modifier.size(22.dp),
        )
    }
}