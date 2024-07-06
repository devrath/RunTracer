@file:OptIn(ExperimentalFoundationApi::class)

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.istudio.core.presentation.designsystem.RunTracerTheme
import com.istudio.core.presentation.designsystem.components.RunTracerActionButton
import com.istudio.core.presentation.designsystem.components.RunTracerOutlinedActionButton
import com.istudio.core.presentation.designsystem.components.RunTracerPasswordRequirement
import com.istudio.core.presentation.designsystem.components.RunTracerPasswordTextField
import com.istudio.core.presentation.designsystem.preview.WindowSizeClassPreviews

// <-------------------------------- RunTracerPasswordRequirement --------------------------------->
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
// <-------------------------------- RunTracerPasswordRequirement --------------------------------->

// <------------------------------ RunTracerOutlinedActionButton ---------------------------------->
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
// <------------------------------ RunTracerOutlinedActionButton ---------------------------------->

// <------------------------------ RunTracerPasswordTextField ------------------------------------->
@WindowSizeClassPreviews
@Composable
private fun RunTracerTextFieldPasswordNotVisiblePreview() {
    RunTracerTheme {
        RunTracerPasswordTextField(
            state = rememberTextFieldState(),
            hint = "example@test.com",
            title = "Email",
            modifier = Modifier.fillMaxWidth(),
            isPasswordVisible = false,
            onTogglePasswordVisibility = {}
        )
    }
}

@WindowSizeClassPreviews
@Composable
private fun RunTracerTextFieldPasswordVisiblePreview() {
    RunTracerTheme {
        RunTracerPasswordTextField(
            state = rememberTextFieldState(),
            hint = "example@test.com",
            title = "Email",
            modifier = Modifier.fillMaxWidth(),
            isPasswordVisible = true,
            onTogglePasswordVisibility = {}
        )
    }
}
// <------------------------------ RunTracerPasswordTextField ------------------------------------->

// <------------------------------ RunTracerActionButton ------------------------------------------>
@WindowSizeClassPreviews
@Composable
private fun RunTracerActionButtonLoadingEnabledPreview() {
    RunTracerTheme {
        RunTracerActionButton(
            text = "Sign up",
            isLoading = true,
            enabled = true,
            onClick = {}
        )
    }
}

@WindowSizeClassPreviews
@Composable
private fun RunTracerActionButtonNotLoadingEnabledPreview() {
    RunTracerTheme {
        RunTracerActionButton(
            text = "Sign up",
            isLoading = false,
            enabled = true,
            onClick = {}
        )
    }
}

@WindowSizeClassPreviews
@Composable
private fun RunTracerActionButtonNotLoadingNotEnabledPreview() {
    RunTracerTheme {
        RunTracerActionButton(
            text = "Sign up",
            isLoading = false,
            enabled = false,
            onClick = {}
        )
    }
}
// <------------------------------ RunTracerActionButton ------------------------------------------>