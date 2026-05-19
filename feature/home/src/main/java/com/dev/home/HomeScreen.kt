package com.dev.home

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dev.designsystem.component.AppBackground
import com.dev.designsystem.constants.Paddings
import com.dev.ui.cards.BananaSizeSelector
import com.dev.ui.cards.BottomBar
import com.dev.ui.cards.PizzaHeader
import com.dev.ui.cards.PizzaCarousel
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {

    val state by viewModel.state.collectAsState()

    AppBackground {
        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFFD4956A))
            }
            return@AppBackground
        }

        val pagerState = rememberPagerState(pageCount = { state.pizzas.size })

        LaunchedEffect(pagerState.currentPage) {
            viewModel.onPizzaChanged(pagerState.currentPage)
        }

        val currentPizza = state.pizzas.getOrNull(state.selectedPizzaIndex)

        val globalTransitionProgress = remember { Animatable(0f) }

        LaunchedEffect(state.isLoading) {
            if (!state.isLoading) {
                delay(100)
                globalTransitionProgress.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(
                        durationMillis = 1400,
                        easing = FastOutSlowInEasing
                    )
                )
            }
        }

        val p = globalTransitionProgress.value

        val headerProgress = ((p - 0.2f) / 0.8f).coerceIn(0f, 1f)
        val carouselProgress = (p / 0.7f).coerceIn(0f, 1f)
        val contentProgress = ((p - 0.2f) / 0.7f).coerceIn(0f, 1f)
        val bottomBarProgress = ((p - 0.3f) / 0.7f).coerceIn(0f, 1f)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Paddings.MEDIUM_PADDING),
        ) {

            Spacer(Modifier.height(Paddings.MEDIUM_PADDING))

            PizzaHeader(
                name = currentPizza?.name.orEmpty(),
                onBackClick = { },
                onFavoriteClick = {  },
                iconAnimationProgress = headerProgress
            )

            Spacer(Modifier.height(Paddings.MEDIUM_PADDING))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        alpha = carouselProgress
                        scaleX = 0.8f + (carouselProgress * 0.2f)
                        scaleY = 0.8f + (carouselProgress * 0.2f)
                    }
            ) {
                PizzaCarousel(
                    pizzas = state.pizzas,
                    pagerState = pagerState,
                    selectedSize = state.selectedSize,
                )
            }

            if (currentPizza != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer {
                            alpha = contentProgress
                            translationY = (1f - contentProgress) * 50f
                        }
                ) {
                    BananaSizeSelector(
                        pizza = currentPizza,
                        selectedSize = state.selectedSize,
                        onSizeSelected = viewModel::onSizeSelected,
                    )
                }
            }

            Spacer(Modifier.height(Paddings.XX_LARGE_PADDING))

            Text(
                text = currentPizza?.description.orEmpty(),
                style = MaterialTheme.typography.displaySmall.copy(
                    fontSize = 20.sp,
                    lineHeight = 28.sp
                ),
                color = Color(0xFF3D3D3D),
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        alpha = contentProgress
                        translationY = (1f - contentProgress) * 60f
                    }
                    .padding(horizontal = Paddings.LARGE_PADDING),
            )

            Spacer(Modifier.height(Paddings.LARGE_PADDING))

            if (currentPizza != null) {
                val variant = currentPizza.variants.find { it.size == state.selectedSize }
                    ?: currentPizza.variants.firstOrNull()

                val total = (variant?.price ?: 0.0) * state.quantity

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer {
                            alpha = bottomBarProgress
                            translationY = (1f - bottomBarProgress) * 70f
                        }
                ) {
                    BottomBar(
                        quantity = state.quantity,
                        totalPrice = total,
                        onIncrement = viewModel::onPlusClick,
                        onDecrement = viewModel::onMinusClick,
                        onAddClick = {  },
                    )
                }
            }

            Spacer(Modifier.height(Paddings.LARGE_PADDING))
        }
    }
}