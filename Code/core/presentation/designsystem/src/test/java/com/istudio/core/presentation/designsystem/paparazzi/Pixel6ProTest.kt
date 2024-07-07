@file:OptIn(ExperimentalFoundationApi::class)

package com.istudio.core.presentation.designsystem.paparazzi

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.ui.Modifier
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.istudio.core.presentation.designsystem.components.RunTracerActionButton
import com.istudio.core.presentation.designsystem.components.RunTracerOutlinedActionButton
import com.istudio.core.presentation.designsystem.components.RunTracerPasswordRequirement
import com.istudio.core.presentation.designsystem.components.RunTracerPasswordTextField
import org.junit.Test
import org.junit.Rule

class Pixel6ProTest {

    @get:Rule
    val pixel6pro = Paparazzi(deviceConfig = DeviceConfig.PIXEL_6_PRO)

    @Test
    fun `Test in Pixel6Pro for the widget RunTracerActionButton`() { pixel6pro.snapshot {
        RunTracerActionButton(
            text = "Sign up",
            isLoading = false,
            enabled = false,
            onClick = {}
        )
    } }

    @Test
    fun `Test in Pixel6Pro for the widget RunTracerOutlinedActionButton`() { pixel6pro.snapshot {
        RunTracerOutlinedActionButton(
            text = "Sign In",
            isLoading = false,
            onClick = {}
        )
    } }

    @Test
    fun `Test in Pixel6Pro for the widget RunTracerPasswordRequirement`() { pixel6pro.snapshot {
        RunTracerPasswordRequirement(
            text = "Length of the password",
            isValid = true
        )
    } }

    @Test
    fun `Test in Pixel6Pro for the widget RunTracerPasswordTextField`() { pixel6pro.snapshot {
        RunTracerPasswordTextField(
            state = rememberTextFieldState(),
            hint = "example@test.com",
            title = "Email",
            modifier = Modifier.fillMaxWidth(),
            isPasswordVisible = true,
            onTogglePasswordVisibility = {}
        )
    } }
}