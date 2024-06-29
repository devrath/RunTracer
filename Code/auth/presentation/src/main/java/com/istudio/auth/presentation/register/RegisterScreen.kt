@file:OptIn(ExperimentalFoundationApi::class)

package com.istudio.auth.presentation.register

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.istudio.auth.domain.PasswordValidationState
import com.istudio.auth.presentation.R
import com.istudio.core.presentation.designsystem.RunTracerTheme
import com.istudio.core.presentation.designsystem.components.GradientBackground
import com.istudio.core.presentation.designsystem.components.RunTracerText
import com.istudio.core.presentation.designsystem.preview.WindowSizeClassPreviews
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreenRoot(
    onSignInClick: () -> Unit,
    onSuccessfulRegistration: () -> Unit,
    viewModel: RegisterViewModel = koinViewModel()
){
    RegisterScreen(
        state = viewModel.state,
        onAction= viewModel::onAction
    )
}

@Composable
private fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit
) {
    GradientBackground {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(vertical = 32.dp)
                .padding(top = 16.dp)
        ) {
            RunTracerText(
                text = stringResource(id = R.string.create_account),
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}


@WindowSizeClassPreviews
@Composable
private fun RegisterScreenPreview() {
    RunTracerTheme {
        RegisterScreen(
            state = RegisterState(
                passwordValidationState = PasswordValidationState(
                    hasNumber = true
                )
            ),
            onAction = {}
        )
    }
}
