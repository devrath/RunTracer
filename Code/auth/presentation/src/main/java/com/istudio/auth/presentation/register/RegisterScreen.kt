@file:OptIn(ExperimentalFoundationApi::class)

package com.istudio.auth.presentation.register

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.istudio.auth.domain.PasswordValidationState
import com.istudio.auth.domain.UserDataValidator
import com.istudio.auth.presentation.R
import com.istudio.core.presentation.designsystem.CheckIcon
import com.istudio.core.presentation.designsystem.CrossIcon
import com.istudio.core.presentation.designsystem.EmailIcon
import com.istudio.core.presentation.designsystem.RunTracerTheme
import com.istudio.core.presentation.designsystem.RuniqueDarkRed
import com.istudio.core.presentation.designsystem.RuniqueGray
import com.istudio.core.presentation.designsystem.RuniqueGreen
import com.istudio.core.presentation.designsystem.components.GradientBackground
import com.istudio.core.presentation.designsystem.components.RunTracerActionButton
import com.istudio.core.presentation.designsystem.components.RunTracerPasswordRequirement
import com.istudio.core.presentation.designsystem.components.RunTracerPasswordTextField
import com.istudio.core.presentation.designsystem.components.RunTracerText
import com.istudio.core.presentation.designsystem.components.RunTracerTextField
import com.istudio.core.presentation.designsystem.preview.WindowSizeClassPreviews
import com.istudio.core.presentation.ui.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreenRoot(
    modifier: Modifier = Modifier,
    onSignInClick: () -> Unit,
    onSuccessfulRegistration: () -> Unit,
    viewModel: RegisterViewModel = koinViewModel()
){
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    ObserveAsEvents(viewModel.events) { event ->
        when(event) {
            is RegisterEvent.Error -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    event.error.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
            RegisterEvent.RegistrationSuccess -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    R.string.registration_successful,
                    Toast.LENGTH_LONG
                ).show()
                onSuccessfulRegistration()
            }
        }
    }


    RegisterScreen(
        state = viewModel.state,
        onAction= viewModel::onAction
    )
}

@Composable
fun RegisterScreen(
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
                color = MaterialTheme.colorScheme.primary,
                text = stringResource(id = R.string.create_account),
                style = MaterialTheme.typography.headlineMedium
            )

            val annotatedTag = "clickable_text"
            val annotatedString = annotatedHeading(annotatedTag)

            ClickableText(
                text = annotatedString,
                onClick = { offset ->
                    annotatedString.getStringAnnotations(
                        tag = annotatedTag,
                        start = offset,
                        end = offset
                    ).firstOrNull()?.let {
                        onAction(RegisterAction.OnLoginClick)
                    }
                }
            )

            Spacer(modifier = Modifier.height(48.dp))

            RunTracerTextField(
                state = state.email,
                startIcon = EmailIcon,
                endIcon = if (state.isEmailValid) { CheckIcon } else null,
                hint = stringResource(id = R.string.example_email),
                title = stringResource(id = R.string.email),
                additionalInfo = stringResource(id = R.string.must_be_a_valid_email),
                keyboardType = KeyboardType.Email,
                testTag = stringResource(id = R.string.email),
            )

            Spacer(modifier = Modifier.height(16.dp))

            RunTracerPasswordTextField(
                state = state.password,
                isPasswordVisible = state.isPasswordVisible,
                onTogglePasswordVisibility = {
                    onAction(RegisterAction.OnTogglePasswordVisibilityClick)
                },
                hint = stringResource(id = R.string.password),
                title = stringResource(id = R.string.password),
                testTag = stringResource(id = R.string.password)
            )

            Spacer(modifier = Modifier.height(16.dp))

            RunTracerPasswordRequirement(
                text = stringResource(
                    id = R.string.at_least_x_characters,
                    UserDataValidator.MIN_PASSWORD_LENGTH
                ),
                isValid = state.passwordValidationState.hasMinLength
            )
            Spacer(modifier = Modifier.height(4.dp))
            RunTracerPasswordRequirement(
                text = stringResource(
                    id = R.string.at_least_one_number,
                ),
                isValid = state.passwordValidationState.hasNumber
            )
            Spacer(modifier = Modifier.height(4.dp))
            RunTracerPasswordRequirement(
                text = stringResource(
                    id = R.string.contains_lowercase_char,
                ),
                isValid = state.passwordValidationState.hasLowerCaseCharacter
            )
            Spacer(modifier = Modifier.height(4.dp))
            RunTracerPasswordRequirement(
                text = stringResource(
                    id = R.string.contains_uppercase_char,
                ),
                isValid = state.passwordValidationState.hasUpperCaseCharacter
            )
            Spacer(modifier = Modifier.height(32.dp))
            RunTracerActionButton(
                text = stringResource(id = R.string.register),
                isLoading = state.isRegistering,
                enabled = state.canRegister,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onAction(RegisterAction.OnRegisterClick)
                }
            )

        }
    }
}

@Composable
private fun annotatedHeading(annotatedTag: String) = buildAnnotatedString {
    // Entire string has one font
    withStyle(
        style = SpanStyle(
            color = RuniqueGray
        )
    ) {
        append(stringResource(id = R.string.already_have_an_account))
        append(" ")
        // Everything following pushStringAnnotation is clickable
        pushStringAnnotation(
            tag = annotatedTag,
            annotation = stringResource(id = R.string.login)
        )
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
            )
        ) {
            append(stringResource(id = R.string.login))
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
