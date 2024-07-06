@file:OptIn(ExperimentalFoundationApi::class)

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.istudio.core.presentation.designsystem.RunTracerTheme
import com.istudio.core.presentation.designsystem.components.RunTracerPasswordTextField
import com.istudio.core.presentation.designsystem.preview.WindowSizeClassPreviews


@Preview
@Composable
private fun RunTracerTextFieldPreview() {
    RunTracerTheme {
        RunTracerPasswordTextField(
            state = rememberTextFieldState(),
            hint = "example@test.com",
            title = "Email",
            modifier = Modifier
                .fillMaxWidth(),
            isPasswordVisible = false,
            onTogglePasswordVisibility = {}
        )
    }
}