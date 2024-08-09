package com.istudio.run.presentation.active_run

import com.istudio.core.domain.location.Location
import com.istudio.run.domain.RunData
import kotlin.time.Duration


/**
 * This will hold all the data values related to a single screen
 */
data class ActiveRunState(
    val elapsedDuration: Duration = Duration.ZERO,
    val shouldTrack: Boolean = false,
    val hasStartedRunning: Boolean = false,
    val currentLocation: Location? = null, // We have null because, Initially we don't have the location. We get later once we start tracking
    val isRunFinished: Boolean = false,
    val isRunSaved: Boolean = false,
    val runData: RunData = RunData(),
    val showLocationRationale: Boolean = false, // If true, show a dialog
    val showNotificationRationale: Boolean = false, // If true, show a dialog
)
