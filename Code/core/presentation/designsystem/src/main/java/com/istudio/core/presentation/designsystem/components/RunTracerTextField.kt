@file:OptIn(ExperimentalFoundationApi::class)

package com.istudio.core.presentation.designsystem.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.input.TextFieldLineLimits
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.istudio.core.presentation.designsystem.CheckIcon
import com.istudio.core.presentation.designsystem.EmailIcon
import com.istudio.core.presentation.designsystem.RunTracerTheme
import com.istudio.core.presentation.designsystem.preview.WindowSizeClassPreviews

@Composable
fun RunTracerTextField(
    state: TextFieldState,
    startIcon: ImageVector?,
    endIcon: ImageVector?,
    hint: String,
    title: String?,
    modifier: Modifier = Modifier,
    error: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    additionalInfo: String? = null,
    testTag: String? = null
) {
    var isFocused by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
    ) {
        // <------------------------- FIRST-ROW ------------------------->
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (title != null) {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            if (error != null) {
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp
                )
            } else if (additionalInfo != null) {
                Text(
                    text = additionalInfo,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp
                )
            }
        }
        // <------------------------- FIRST-ROW ------------------------->

        // <------------------------- SECOND-ROW ------------------------>
        Spacer(modifier = Modifier.height(4.dp))
        // <------------------------- SECOND-ROW ------------------------>

        // <------------------------- THIRD-ROW ------------------------>
        val textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onBackground)
        val keyboardOptions = KeyboardOptions(keyboardType = keyboardType)

        val viewBackground = if (isFocused) {
            // If focused -> Add a alpha value to surface color
            MaterialTheme.colorScheme.primary.copy(alpha = 0.05f)
        } else {
            // Else -> Use the surface color
            MaterialTheme.colorScheme.surface
        }
        val fieldPadding = 12.dp
        val borderWidth = 1.dp
        val borderShape = RoundedCornerShape(16.dp)
        val borderColor = if (isFocused) {
            // If focused -> Add a color
            MaterialTheme.colorScheme.primary
        } else {
            // Else -> Remove the color
            Color.Transparent
        }

        val fieldModifier =  Modifier
            .clip(borderShape)
            .background(viewBackground)
            .border(width = borderWidth, color = borderColor, shape = borderShape)
            .padding(fieldPadding)
            .onFocusChanged {
                isFocused = it.isFocused
            }
            .testTag(testTag?:"")

        BasicTextField2(
            state = state,
            textStyle = textStyle,
            keyboardOptions = keyboardOptions,
            lineLimits = TextFieldLineLimits.SingleLine,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
            modifier = fieldModifier,
            decorator = { innerBox ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (startIcon != null) {
                        Icon(
                            imageVector = startIcon,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        if (state.text.isEmpty() && !isFocused) {
                            Text(
                                text = hint,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                    alpha = 0.4f
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        innerBox()
                    }
                    if (endIcon != null) {
                        Spacer(modifier = Modifier.width(16.dp))
                        Icon(
                            imageVector = endIcon,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier
                                .padding(end = 8.dp)
                        )
                    }
                }
            }
        )
        // <------------------------- THIRD-ROW ------------------------>
    }
}

@WindowSizeClassPreviews
@Composable
private fun RunTracerTextFieldPreview() {
    RunTracerTheme {
        RunTracerTextField(
            state = rememberTextFieldState(),
            startIcon = EmailIcon,
            endIcon = CheckIcon,
            hint = "example@test.com",
            title = "Email",
            additionalInfo = "Must be a valid email",
            modifier = Modifier.fillMaxWidth()
        )
    }
}