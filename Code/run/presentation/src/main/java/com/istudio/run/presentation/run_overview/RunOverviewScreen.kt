@file:OptIn(ExperimentalMaterial3Api::class)

package com.istudio.run.presentation.run_overview

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.istudio.core.presentation.designsystem.AnalyticsIcon
import com.istudio.core.presentation.designsystem.LogoutIcon
import com.istudio.core.presentation.designsystem.RunIcon
import com.istudio.core.presentation.designsystem.RunTracerTheme
import com.istudio.core.presentation.designsystem.components.RuniqueToolbar
import com.istudio.core.presentation.designsystem.components.util.DropDownItem
import com.istudio.run.presentation.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun RunOverviewScreenRoot(
    viewModel: RunOverviewViewModel = koinViewModel(),
) {
    RunOverviewScreen(
        onAction = viewModel::onAction
    )
}

@Composable
private fun RunOverviewScreen(
    onAction: (RunOverviewAction) -> Unit
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = topAppBarState
    )
    RuniqueScaffold(
        topAppBar = {
            RuniqueToolbar(
                showBackButton = false,
                title = stringResource(id = R.string.run_tracer),
                scrollBehavior = scrollBehavior,
                menuItems = listOf(
                    DropDownItem(
                        icon = AnalyticsIcon,
                        title = stringResource(id = R.string.analytics)
                    ),
                    DropDownItem(
                        icon = LogoutIcon,
                        title = stringResource(id = R.string.logout)
                    ),
                ),
                onMenuItemClick = { index ->
                    when (index) {
                        0 -> onAction(RunOverviewAction.OnAnalyticsClick)
                        1 -> onAction(RunOverviewAction.OnLogoutClick)
                    }
                },
                startContent = {
                    Icon(
                        imageVector = LogoIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(30.dp)
                    )
                }
            )
        },
        floatingActionButton = {
            RuniqueFloatingActionButton(
                icon = RunIcon,
                onClick = {
                    onAction(RunOverviewAction.OnStartClick)
                }
            )
        }
    ) { padding ->

    }
}

@Preview
@Composable
private fun RunOverviewScreenPreview() {
    RunTracerTheme {
        RunOverviewScreen(
            onAction = {}
        )
    }
}
