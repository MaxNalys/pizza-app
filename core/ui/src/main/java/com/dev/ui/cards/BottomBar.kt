package com.dev.ui.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dev.designsystem.R
import com.dev.designsystem.component.PizzaAppButton
import com.dev.designsystem.constants.Paddings

@Composable
fun BottomBar(
    quantity: Int,
    totalPrice: Double,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onAddClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = Paddings.X_LARGE_PADDING,
                vertical = Paddings.LARGE_PADDING
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            modifier = Modifier
                .height(48.dp)
                .background(Color(0xFFEFEBE8), RoundedCornerShape(24.dp))
                .padding(horizontal = Paddings.SMALL_PADDING),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Paddings.LARGE_PADDING),
        ) {
            QtyActionCircle(
                label = stringResource(R.string.action_decrement),
                onClick = onDecrement,
                adjustBottomPadding = true,
            )

            Text(
                text = quantity.toString(),
                style = MaterialTheme.typography.displaySmall.copy(fontSize = 18.sp),
                color = Color(0xFF1E1E1E),
            )

            QtyActionCircle(
                label = stringResource(R.string.action_increment),
                onClick = onIncrement,
            )
        }

        Text(
            text = stringResource(R.string.price_format, totalPrice),
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 33.sp,
                lineHeight = 38.sp
            ),
            color = Color(0xFF1E1E1E),
        )

        PizzaAppButton(
            text = stringResource(R.string.action_add),
            onClick = onAddClick,
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier.width(105.dp)
        )

    }
}

@Composable
private fun QtyActionCircle(
    label: String,
    onClick: () -> Unit,
    adjustBottomPadding: Boolean = false,
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Color.White)
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = label,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF1E1E1E),
            modifier = Modifier.padding(bottom = if (adjustBottomPadding) 2.dp else 0.dp)
        )
    }
}