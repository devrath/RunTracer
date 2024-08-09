package com.istudio.run.presentation.active_run.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.istudio.run.presentation.active_run.ActiveRunAction
import com.istudio.run.presentation.active_run.ActiveRunEvent
import com.istudio.run.presentation.active_run.ActiveRunState
import kotlinx.coroutines.channels.Channel
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

    /**
     * UI communicates to the view model
     *
     * @param action Ui layer sends this action to the view model
     */
    fun onAction(action: ActiveRunAction) {

    }

}