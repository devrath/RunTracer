package com.istudio.auth.presentation.intro

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import com.istudio.auth.presentation.R
import com.istudio.core.presentation.designsystem.RunTracerTheme
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class IntroScreenKtTest {

    @get:Rule
    val composeRule = createComposeRule()


    @Test
    fun testIntroScreen_whetherInitialUiIsLoadedProperly() {

        val context = InstrumentationRegistry.getInstrumentation().context

        val appTitle = context.getString(R.string.run_tracer)
        val welcomeText = context.getString(R.string.welcome_to_runtracer)
        val descriptionText = context.getString(R.string.runtracer_description)


        // <--------- Set the content with states --------------->
        composeRule.setContent {
            RunTracerTheme {
                IntroScreen(
                    onAction = {},
                )
            }
        }

        // <--------- Perform the assertions for the UI --------->


        composeRule.onNodeWithText(appTitle)
            // perform assertion
            .assertIsDisplayed()

    }



}