package com.istudio.auth.presentation.intro

import androidx.compose.ui.test.junit4.createComposeRule
import com.istudio.auth.presentation.robots.IntroScreenRobot
import org.junit.Rule
import org.junit.Test

class IntroScreenKtTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val robot = IntroScreenRobot(composeRule)

    @Test
    fun testIntroScreen_whetherInitialUiIsLoadedProperly() {
        // Load IntroScreen
        robot.loadIntroScreen()
        // Perform assertions
        robot.validateUiLoaded()
    }


}