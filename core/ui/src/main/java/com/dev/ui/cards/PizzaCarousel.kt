package com.dev.ui.cards

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dev.model.Pizza
import kotlinx.coroutines.launch

@Composable
fun PizzaCarousel(
    pizzas: List<Pizza>,
    pagerState: PagerState,
    selectedSize: String,
) {
    val scope = rememberCoroutineScope()

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp),

        contentPadding = PaddingValues(horizontal = 50.dp),

        pageSpacing = -35.dp,
    ) { page ->

        val pizza = pizzas[page]
        val isCurrent = page == pagerState.currentPage

        PizzaCarouselItem(
            pizza = pizza,
            isCurrent = isCurrent,
            selectedSize = if (isCurrent) selectedSize else "M",

            onClick = {
                scope.launch {
                    pagerState.animateScrollToPage(page)
                }
            }
        )
    }
}