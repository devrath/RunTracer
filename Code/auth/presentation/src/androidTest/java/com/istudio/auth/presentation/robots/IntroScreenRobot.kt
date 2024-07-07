package com.istudio.auth.presentation.robots

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import com.istudio.auth.presentation.R
import com.istudio.auth.presentation.intro.IntroScreen
import com.istudio.core.presentation.designsystem.RunTracerTheme

internal class IntroScreenRobot(private val composeRule: ComposeContentTestRule) {
    private val context = InstrumentationRegistry.getInstrumentation().context

    private val appTitle = context.getString(R.string.run_tracer)
    private val welcomeText = context.getString(R.string.welcome_to_runtracer)
    private val descriptionText = context.getString(R.string.runtracer_description)
    private val signInText = context.getString(R.string.sign_in)
    private val signUpText = context.getString(R.string.sign_up)

    /**
     * Loading and setting the intro screen
     */
    fun loadIntroScreen() {
        composeRule.setContent {
            RunTracerTheme {
                IntroScreen(
                    onAction = {},
                )
            }
        }
    }

    /**
     * Check if all the UI elements are properly loaded
     */
    fun validateUiLoaded() {
        composeRule.apply {
            onNodeWithText(appTitle).assertIsDisplayed()
            onNodeWithText(welcomeText).assertIsDisplayed()
            onNodeWithText(descriptionText).assertIsDisplayed()
            onNodeWithText(signInText).assertIsDisplayed()
            onNodeWithText(signUpText).assertIsDisplayed()
        }
    }

}

