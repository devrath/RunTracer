@file:OptIn(ExperimentalMaterial3Api::class)

package com.istudio.run.presentation.active_run

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.activity.ComponentActivity
import com.istudio.core.presentation.designsystem.StartIcon
import com.istudio.core.presentation.designsystem.StopIcon
import com.istudio.core.presentation.designsystem.components.RunTracerFloatingActionButton
import com.istudio.core.presentation.designsystem.components.RunTracerScaffold
import com.istudio.core.presentation.designsystem.components.RunTracerToolbar
import com.istudio.core.presentation.designsystem.preview.WindowSizeClassPreviews
import com.istudio.run.presentation.R
import com.istudio.run.presentation.active_run.components.RunDataCard
import com.istudio.run.presentation.util.shouldShowLocationPermissionRationale
import com.istudio.run.presentation.util.shouldShowNotificationPermissionRationale
import org.koin.androidx.compose.koinViewModel

@Composable
fun ActiveRunScreenRoot(
    viewModel: ActiveRunViewModel = koinViewModel(),
) {
    ActiveRunScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun ActiveRunScreen(
    state: ActiveRunState,
    onAction: (ActiveRunAction) -> Unit
) {
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        //  Check if the course location permission is given
        val hasCourseLocationPermission = perms[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        //  Check if the fine location permission is given
        val hasFineLocationPermission = perms[Manifest.permission.ACCESS_FINE_LOCATION] == true
        // Check if the notification permission is given
        val hasNotificationPermission = if (Build.VERSION.SDK_INT >= 33) {
            perms[Manifest.permission.POST_NOTIFICATIONS] == true
        } else true

        val activity = context as ComponentActivity
        val showLocationRationale = activity.shouldShowLocationPermissionRationale()
        val showNotificationRationale = activity.shouldShowNotificationPermissionRationale()

        val locationPermissionState = hasCourseLocationPermission && hasFineLocationPermission

        // We take the required information and submit to the view model --> The view model will decide how to deal with it
        onAction(
            ActiveRunAction.SubmitLocationPermissionInfo(
                acceptedLocationPermission = locationPermissionState,
                showLocationRationale = showLocationRationale
            )
        )
        onAction(
            ActiveRunAction.SubmitNotificationPermissionInfo(
                acceptedNotificationPermission = hasNotificationPermission,
                showNotificationPermissionRationale = showNotificationRationale
            )
        )
        
    }


    RunTracerScaffold(
        withGradient = false,
        topAppBar = {
            RunTracerToolbar(
                showBackButton = true,
                title = stringResource(id = R.string.active_run),
                onBackClick = { onAction(ActiveRunAction.OnBackClick) }
            )
        },
        floatingActionButton = {
            RunTracerFloatingActionButton(
                icon = when {
                    state.shouldTrack -> StopIcon
                    else -> StartIcon
                },
                onClick = { onAction(ActiveRunAction.OnToggleRunClick) },
                iconSize = 20.dp,
                contentDescription = when {
                    state.shouldTrack -> stringResource(id = R.string.pause_run)
                    else -> stringResource(id = R.string.start_run)
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            RunDataCard(
                elapsedTime = state.elapsedDuration,
                runData = state.runData,
                modifier = Modifier
                    .padding(16.dp)
                    .padding(padding)
                    .fillMaxWidth()
            )
        }
    }

}

@Preview
@WindowSizeClassPreviews
@Composable
fun ActiveRunScreenPreview(
    modifier: Modifier = Modifier
) {
    ActiveRunScreen(
        state = ActiveRunState(),
        onAction = {

        }
    )
}