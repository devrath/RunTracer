package com.istudio.core.presentation.designsystem.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.istudio.core.presentation.designsystem.CheckIcon
import com.istudio.core.presentation.designsystem.CrossIcon
import com.istudio.core.presentation.designsystem.RunTracerTheme
import com.istudio.core.presentation.designsystem.RuniqueDarkRed
import com.istudio.core.presentation.designsystem.RuniqueGreen
import com.istudio.core.presentation.designsystem.preview.WindowSizeClassPreviews

@Composable
fun RunTracerPasswordRequirement(
    text: String,
    isValid: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (isValid) {
                CheckIcon
            } else {
                CrossIcon
            },
            contentDescription = null,
            tint = if(isValid) RuniqueGreen else RuniqueDarkRed
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp
        )
    }
}

@WindowSizeClassPreviews
@Composable
private fun RunTracerPasswordRequirementValidPreview() {
    RunTracerTheme {
        RunTracerPasswordRequirement(
            text = "Length of the password",
            isValid = true
        )
    }
}

@WindowSizeClassPreviews
@Composable
private fun RunTracerPasswordRequirementInvalidPreview() {
    RunTracerTheme {
        RunTracerPasswordRequirement(
            text = "Length of the password",
            isValid = false
        )
    }
}