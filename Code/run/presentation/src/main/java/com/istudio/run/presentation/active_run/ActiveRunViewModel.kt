package com.istudio.run.presentation.active_run

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

/**
 * Manages the responsibilities to ActiveRunScreen
 *
 * Responsibilities:
 * - Observes the actions from the ActiveRunScreen
 * - Notifies the events to the ActiveRunScreen
 */
class ActiveRunViewModel: ViewModel() {

    // Holds the data state of the view
    var state by mutableStateOf(ActiveRunState())
        private set

    // ViewModel communicates to view using the channel
    private val eventChannel = Channel<ActiveRunEvent>()
    val events = eventChannel.receiveAsFlow()

    // Keep track if the screen has the permission 
    private val _hasLocationPermission = MutableStateFlow(false)

    /**
     * UI communicates to the view model
     *
     * @param action Ui layer sends this action to the view model
     */
    fun onAction(action: ActiveRunAction) {
        when(action) {
            ActiveRunAction.OnFinishRunClick -> {

            }
            ActiveRunAction.OnResumeRunClick -> {

            }
            ActiveRunAction.OnToggleRunClick -> {

            }
            is ActiveRunAction.SubmitLocationPermissionInfo -> {
                _hasLocationPermission.value = action.acceptedLocationPermission
                state = state.copy(
                    showLocationRationale = action.showLocationRationale
                )
            }
            is ActiveRunAction.SubmitNotificationPermissionInfo -> {
                state = state.copy(
                    showNotificationRationale = action.showNotificationPermissionRationale
                )
            }
            is ActiveRunAction.DismissRationaleDialog -> {
                state = state.copy(
                    showNotificationRationale = false,
                    showLocationRationale = false
                )
            }
            else -> Unit
        }
    }

}