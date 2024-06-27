package com.istudio.auth.presentation.intro

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.istudio.auth.presentation.R
import com.istudio.core.presentation.designsystem.LogoIcon
import com.istudio.core.presentation.designsystem.RunTracerTheme
import com.istudio.core.presentation.designsystem.components.GradientBackground
import com.istudio.core.presentation.designsystem.components.RunTracerActionButton
import com.istudio.core.presentation.designsystem.components.RunTracerOutlinedActionButton
import com.istudio.core.presentation.designsystem.components.RunTracerText
import com.istudio.core.presentation.designsystem.preview.WindowSizeClassPreviews

@Composable
fun IntroScreenRoot(
    onSignUpClick: () -> Unit,
    onSignInClick: () -> Unit,
) {
    IntroScreen(
        onAction = { action ->
            when (action) {
                IntroAction.OnSignInClick -> onSignInClick()
                IntroAction.OnSignUpClick -> onSignUpClick()
            }
        },
    )
}

@Composable
fun IntroScreen(
    modifier: Modifier = Modifier,
    onAction: (IntroAction) -> Unit,
) {
    GradientBackground {
        Box(
            modifier =
            modifier
                .fillMaxWidth()
                .weight(1f)
                .align(alignment = Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center,
        ) {
            RunTracerLogoVertical()
        }
        Column(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(bottom = 48.dp),
        ) {
            RunTracerText(
                text = stringResource(id = R.string.welcome_to_runtracer),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Spacer(modifier = Modifier.height(8.dp))
            RunTracerText(
                text = stringResource(id = R.string.runtracer_description),
                style = MaterialTheme.typography.bodySmall,
            )
            Spacer(modifier = Modifier.height(32.dp))
            RunTracerOutlinedActionButton(
                text = stringResource(id = R.string.sign_in),
                isLoading = false,
                onClick = {
                    onAction(IntroAction.OnSignInClick)
                },
                modifier =
                Modifier
                    .fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(16.dp))
            RunTracerActionButton(
                text = stringResource(id = R.string.sign_up),
                isLoading = false,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onAction(IntroAction.OnSignUpClick)
                },
            )
        }
    }
}

@Composable
private fun RunTracerLogoVertical(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = LogoIcon,
            contentDescription = "Logo",
            tint = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.height(RunTracerTheme.dimen.introScreenDimen.logoBrandNameSpacing))
        RunTracerText(
            text = stringResource(id = R.string.run_tracer),
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@WindowSizeClassPreviews
@Composable
private fun IntroScreenPreview() {
    RunTracerTheme {
        IntroScreen(
            onAction = {},
        )
    }
}
