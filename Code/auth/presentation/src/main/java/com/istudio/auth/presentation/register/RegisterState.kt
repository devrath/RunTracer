@file:OptIn(ExperimentalFoundationApi::class)

package com.istudio.auth.presentation.register

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import com.istudio.auth.domain.PasswordValidationState

data class RegisterState constructor(
    val email: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),
    val passwordValidationState: PasswordValidationState = PasswordValidationState(),
    val isEmailValid: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val isRegistering: Boolean = false,
    val canRegister: Boolean = passwordValidationState.isValidPassword && !isRegistering
)
