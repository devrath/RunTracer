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
    fun tesRegisterScreen_whetherInitialUiIsLoadedProperly() {
        // Load IntroScreen
        robot.loadRegisterScreen()
        // Perform assertions
        robot.validateUiLoaded()
    }

}