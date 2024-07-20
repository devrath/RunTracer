package com.istudio.auth.presentation.register

import androidx.compose.ui.test.junit4.createComposeRule
import com.istudio.auth.presentation.robots.RegisterScreenRobot
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class RegisterScreenKtTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val robot = RegisterScreenRobot(composeRule)

    @Test
    fun testRegisterScreen_whetherInitialUiIsLoadedProperly() {
        robot.apply {
            loadRegisterScreen()
            validateUiLoaded()
        }
    }

    @Test
    fun testRegisterScreen_inputIsValidEmailEntered() {
        robot.apply {
            loadRegisterScreen()
            inputValidEmail()
            validateEnteredEmail()
        }
    }

    @Test
    fun testRegisterScreen_inputIsValidPasswordEntered() {
        robot.apply {
            loadRegisterScreen()
            inputValidPassword()
            validateEnteredPassword()
        }
    }

}