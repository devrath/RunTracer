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
import com.istudio.auth.domain.AuthRepository
import com.istudio.auth.domain.UserDataValidator
import com.istudio.auth.presentation.R
import com.istudio.core.domain.util.DataError
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import com.istudio.core.domain.util.Result.Error
import com.istudio.core.domain.util.Result
import com.istudio.core.presentation.ui.UiText
import com.istudio.core.presentation.ui.asUiText

class RegisterViewModel(
    private val userDataValidator: UserDataValidator,
    private val repository: AuthRepository
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

    /**
     * Sending the events from the view model to the UI
     */
    private val eventChannel = Channel<RegisterEvent>()
    val events = eventChannel.receiveAsFlow()

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
        when(action) {
            RegisterAction.OnRegisterClick -> register()
            RegisterAction.OnTogglePasswordVisibilityClick -> {
                state = state.copy(
                    isPasswordVisible = !state.isPasswordVisible
                )
            }
            else -> Unit
        }
    }


    /**
     * Registers a new user.
     */
    private fun register() {
        viewModelScope.launch {
            state = state.copy(isRegistering = true)
            val result = repository.register(
                email = state.email.text.toString().trim(),
                password = state.password.text.toString()
            )
            state = state.copy(isRegistering = false)

            when(result) {
                is Result.Error -> {
                    if(result.error == DataError.Network.CONFLICT) {
                        eventChannel.send(RegisterEvent.Error(
                            UiText.StringResource(R.string.error_email_exists)
                        ))
                    } else {
                        eventChannel.send(RegisterEvent.Error(result.error.asUiText()))
                    }
                }
                is Result.Success -> {
                    eventChannel.send(RegisterEvent.RegistrationSuccess)
                }
            }
        }
    }

}