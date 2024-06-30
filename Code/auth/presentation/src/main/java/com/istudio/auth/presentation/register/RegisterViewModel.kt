@file:Suppress("OPT_IN_USAGE_FUTURE_ERROR")
@file:OptIn(ExperimentalFoundationApi::class)

package com.istudio.auth.presentation.register

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.istudio.auth.domain.UserDataValidator
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RegisterViewModel(
    private val userDataValidator: UserDataValidator
): ViewModel() {

    /**
     * Holds the current state of the registration screen.
     *
     * This property is used to manage the UI state of the registration screen. It is
     * mutable and can be observed by the UI to react to changes. The state can only
     * be modified within this ViewModel to ensure encapsulation and prevent external
     * modification.
     */
    var state by mutableStateOf(RegisterState())
        private set

    init {
        // 1. Create a flow from text state
        state.email.textAsFlow()
            // 2. On each emission
            .onEach { email ->
                // 3. Update the states
                val isValidEmail = userDataValidator.isValidEmail(email.toString())
                state = state.copy(
                    isEmailValid = isValidEmail,
                    canRegister = isValidEmail && state.passwordValidationState.isValidPassword
                            && !state.isRegistering
                )
            }
            .launchIn(viewModelScope)

        // 1. Create a flow from text state
        state.password.textAsFlow()
            // 2. On each emission
            .onEach { password ->
                // 3. Update the states
                val passwordValidationState = userDataValidator.validatePassword(password.toString())
                state = state.copy(
                    passwordValidationState = passwordValidationState,
                    canRegister = state.isEmailValid && passwordValidationState.isValidPassword
                            && !state.isRegistering
                )
            }
            .launchIn(viewModelScope)
    }


    /**
     * Handles the actions of user for registration screen.
     *
     * This method is invoked when a register action occurs. It allows you to define
     * the behavior that should be executed in response to the given action.
     *
     * @param action The register action to be handled.
     */
    fun onAction(action: RegisterAction) {

    }

}