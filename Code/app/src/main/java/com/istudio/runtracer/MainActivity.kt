package com.istudio.runtracer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.istudio.core.presentation.designsystem.RunTracerTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.state.isCheckingAuth
            }
        }
        enableEdgeToEdge()
        setContent {
            RunTracerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CurrentScreen(Modifier.padding(innerPadding),viewModel)
                }
            }
        }
    }
}

@Composable
fun CurrentScreen(modifier: Modifier = Modifier, viewModel: MainViewModel) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        if(!viewModel.state.isCheckingAuth) {
            val navController = rememberNavController()
            NavigationRoot(
                navController = navController,
                isLoggedIn = viewModel.state.isLoggedIn
            )
        }
    }
}