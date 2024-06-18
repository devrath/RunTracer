package com.istudio.auth.presentation.intro

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.istudio.core.presentation.designsystem.RunTracerTheme


@Composable
fun IntroScreenRoot(
    modifier: Modifier = Modifier,
    onSignUpClick : () -> Unit,
    onSignInClick : () -> Unit
) {
    IntroScreen(
        onAction = { action ->
           when(action){
               IntroAction.OnSignInClick -> onSignInClick()
               IntroAction.OnSignUpClick -> onSignUpClick()
           }
       }
    )
}


@Composable
fun IntroScreen(
    modifier: Modifier = Modifier,
    onAction: (IntroAction) -> Unit
) {



}


@Preview
@Composable
private fun IntroScreenPreview() {
    RunTracerTheme {
        IntroScreen (
            onAction = {}
        )
    }
}