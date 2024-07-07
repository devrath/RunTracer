@file:OptIn(ExperimentalFoundationApi::class)

package com.istudio.auth.presentation.robots

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import com.istudio.auth.domain.PasswordValidationState
import com.istudio.auth.presentation.R
import com.istudio.auth.presentation.register.RegisterScreen
import com.istudio.auth.presentation.register.RegisterState
import com.istudio.core.presentation.designsystem.RunTracerTheme

internal class RegisterScreenRobot(private val composeRule: ComposeContentTestRule) {

    private val context = InstrumentationRegistry.getInstrumentation().context

    private val createAccountText = context.getString(R.string.create_account)
    private val atLeastOneNumber = context.getString(R.string.at_least_one_number)
    private val containsLowerCharacter = context.getString(R.string.contains_lowercase_char)
    private val containsUpperCharacter = context.getString(R.string.contains_uppercase_char)
    private val registerText = context.getString(R.string.register)


    /**
     * Loading and setting the register screen
     */
    fun loadRegisterScreen() {
        composeRule.setContent {
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
    }

    /**
     * Check if all the UI elements are properly loaded
     */
    fun validateUiLoaded() {
        composeRule.apply {
            onNodeWithText(createAccountText).assertIsDisplayed()
            onNodeWithText(atLeastOneNumber).assertIsDisplayed()
            onNodeWithText(containsLowerCharacter).assertIsDisplayed()
            onNodeWithText(containsUpperCharacter).assertIsDisplayed()
            onNodeWithText(registerText).assertIsDisplayed()
        }
    }


}