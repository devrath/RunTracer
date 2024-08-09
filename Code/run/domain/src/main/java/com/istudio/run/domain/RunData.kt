package com.istudio.run.domain

import com.istudio.core.domain.location.Location
import com.istudio.core.domain.location.LocationTimeStamp
import kotlin.time.Duration

data class RunData(
    val distanceMeters: Int = 0,
    val pace: Duration = Duration.ZERO,
    val locations: List<List<LocationTimeStamp>> = emptyList()

)