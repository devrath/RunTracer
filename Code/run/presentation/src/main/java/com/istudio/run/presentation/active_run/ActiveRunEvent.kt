package com.istudio.run.presentation.active_run

import com.istudio.core.presentation.ui.UiText

/**
 * These are the events that are sent from the view-model to the screen
 */
sealed interface ActiveRunEvent {
    // If a error occurred - Notify the UI
    data class Error(val message : UiText) : ActiveRunEvent
    // If the run was successfully saved - Notify the UI
    data object RunSaved : ActiveRunAction
}