package com.istudio.core.presentation.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.istudio.core.presentation.designsystem.RunTracerTheme
import com.istudio.core.presentation.designsystem.components.UiConstants.ROUNDED_CORNER_SHAPE
import com.istudio.core.presentation.designsystem.dimen.RunTracerDimens
import com.istudio.core.presentation.designsystem.preview.WindowSizeClassPreviews

@Composable
fun RunTracerOutlinedActionButton(
    text: String,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground,
        ),
        border = BorderStroke(
            width = 0.5.dp,
            color = MaterialTheme.colorScheme.onBackground
        ),
        shape = RoundedCornerShape(ROUNDED_CORNER_SHAPE),
        modifier = modifier
            .height(IntrinsicSize.Min)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = RunTracerDimens.SpacingScale.dp8),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(RunTracerDimens.SpacingScale.dp15)
                    .alpha(if (isLoading) 1f else 0f),
                strokeWidth = 1.5.dp,
                color = MaterialTheme.colorScheme.onBackground
            )
            RunTracerText(
                text = text,
                modifier = Modifier.alpha(if (isLoading) 0f else 1f),
                fontWeight = FontWeight.Medium
            )
        }
    }
}


@WindowSizeClassPreviews
@Composable
private fun RunTracerOutlinedActionButtonLoadedPreview() {
    RunTracerTheme {
        RunTracerOutlinedActionButton(
            text = "Sign In",
            isLoading = false,
            onClick = {}
        )
    }
}

@WindowSizeClassPreviews
@Composable
private fun RunTracerOutlinedActionButtonLoadingPreview() {
    RunTracerTheme {
        RunTracerOutlinedActionButton(
            text = "Sign In",
            isLoading = true,
            onClick = {}
        )
    }
}