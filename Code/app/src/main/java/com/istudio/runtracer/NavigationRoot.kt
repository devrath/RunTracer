package com.istudio.runtracer

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.istudio.auth.presentation.intro.IntroScreenRoot
import com.istudio.auth.presentation.login.LoginScreenRoot
import com.istudio.auth.presentation.register.RegisterScreenRoot

/**
 * Good practice:
 * 1) Create a navigation graph per feature
 * 2) App must decide the navigation instead of the feature itself,
 *    so we use lambda to delegate the navigation back to the app and let app decide where to navigate the action,
 *    By doing so we can make the app more modular and maintainable & feature re-usable in other apps
 */
@Composable
fun NavigationRoot(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = "auth"
    ) {
        authGraph(navController)
        runGraph(navController)
    }
}

/**
 * The function "authGraph" is an extension function for the NavGraphBuilder class.
 * It is used to define the navigation graph for the authentication feature.
 *
 * Screens for this graph: INTRO, LOGIN, REGISTRATION
 */
private fun NavGraphBuilder.authGraph(navController: NavHostController) {
    navigation(
        startDestination = "intro",
        route = "auth"
    ) {
        // Composable:-> Intro Screen
        composable(route = "intro") {
            IntroScreenRoot(
                onSignUpClick = {
                    navController.navigate("register")
                },
                onSignInClick = {
                    navController.navigate("login")
                }
            )
        }
        // Composable:-> Register Screen
        composable(route = "register") {
            RegisterScreenRoot(
                onSignInClick = {
                    navController.navigate("login") {
                        // We remove the screen but keep the state, So when we move back, We can use the state stored to re-use the state.
                        popUpTo("register") {
                            // Also pop the register screen.
                            inclusive = true
                            // When we pop the backstack, we still keep its state
                            saveState = true
                        }
                        // If there already exists a state --> Just re-use that state and do not create a new state again.
                        restoreState = true
                    }
                },
                onSuccessfulRegistration = {
                    navController.navigate("login")
                }
            )
        }
        composable("login") {
            LoginScreenRoot(
                onLoginSuccess = {
                    navController.navigate("run") {
                        popUpTo("auth") {
                            inclusive = true
                        }
                    }
                },
                onSignUpClick = {
                    navController.navigate("register") {
                        popUpTo("login") {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
                }
            )
        }
    }
}

private fun NavGraphBuilder.runGraph(navController: NavHostController) {
    navigation(
        startDestination = "run_overview",
        route = "run"
    ) {
        composable("run_overview") {
            Text(text = "Run overview!")
        }
    }
}