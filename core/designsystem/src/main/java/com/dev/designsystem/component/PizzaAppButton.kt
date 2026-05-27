package com.dev.designsystem.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dev.designsystem.R
import com.dev.designsystem.constants.Paddings.LARGE_PADDING
import com.dev.designsystem.theme.PizzaAppTheme

private const val ROUNDED_CORNER_PERCENT = 50
private val BUTTON_HEIGHT = 48.dp

@Composable
fun PizzaAppButton(
    text: String,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = LARGE_PADDING),
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(ROUNDED_CORNER_PERCENT),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        contentPadding = contentPadding,
        modifier = modifier.height(BUTTON_HEIGHT)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.displayMedium
        )
    }
}

@Preview
@Composable
private fun PizzaAppButtonPreview(){
    PizzaAppTheme {
        PizzaAppButton(
            text = stringResource(R.string.action_add),
            modifier = Modifier.width(100.dp)
        ) { }
    }
}