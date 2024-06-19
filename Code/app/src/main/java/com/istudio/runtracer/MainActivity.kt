package com.istudio.runtracer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.istudio.auth.presentation.intro.IntroScreenRoot
import com.istudio.core.presentation.designsystem.RunTracerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RunTracerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    IntroScreenRoot(
                        modifier = Modifier.padding(innerPadding),
                        onSignInClick = {

                        },
                        onSignUpClick = {

                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CurrentScreen(modifier: Modifier = Modifier) {
    IntroScreenRoot(
        onSignInClick = {

        },
        onSignUpClick = {

        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RunTracerTheme {
        CurrentScreen()
    }
}