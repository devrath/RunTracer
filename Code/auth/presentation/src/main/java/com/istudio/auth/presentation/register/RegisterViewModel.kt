@file:Suppress("OPT_IN_USAGE_FUTURE_ERROR")

package com.istudio.auth.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class RegisterViewModel: ViewModel() {

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